package carsharing.controller;

import carsharing.dao.h2.CarDaoH2Impl;
import carsharing.dao.h2.CompanyDaoH2Impl;
import carsharing.dao.h2.CustomerDaoH2Impl;
import carsharing.entitie.Car;
import carsharing.entitie.Company;
import carsharing.entitie.Customer;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class Menu {

    public static final Scanner scanner = new Scanner(System.in);

    public static void managementCar(CarDaoH2Impl carDaoH2, CompanyDaoH2Impl companyDaoH2, int companyId) {
        Scanner scanner = new Scanner(System.in);
        if (companyId > 0) {
            String companyName = companyDaoH2.getAllCompanies().get(companyId - 1).getName();
            System.out.println("'" + companyName + "' company");
        } else {
            return;
        }

        for(;;) {
            if (companyDaoH2.getAllCompanies().size() >= companyId) {
                MenuPrinter.companyMenu();
                switch (userChoice()) {
                    case 0:
                        return;
                    case 1:
                        if(carDaoH2.getCars(companyId).size() == 0) {
                            System.out.println("The car list is empty!\n");
                        } else {
                            System.out.println("Car list:");
                            int id = 1;
                            for (Car car : carDaoH2.getCars(companyId)) {
                                System.out.println(id + ". " + car.getName());
                                id++;
                            }
                            System.out.println();
                        }
                        break;
                    case 2:
                        System.out.println("Enter the car name:");
                        carDaoH2.createCar(scanner.nextLine(), companyId);
                        System.out.println("The car was added!\n");
                        break;
                }
            } else {
                return;
            }
        }
    }


    public static void managementCompany(CompanyDaoH2Impl companyDaoH2, CarDaoH2Impl carDaoH2) {
        Scanner scanner = new Scanner(System.in);
        for (;;) {
            MenuPrinter.managerMenu();
            switch (userChoice()) {
                case 0:
                    return;
                case 1:
                    if(companyDaoH2.getAllCompanies().size() == 0) {
                        System.out.println("The company list is empty!\n");
                    } else {
                        System.out.println("Choose the company:");
                        companyDaoH2.getAllCompanies()
                                .stream()
                                .forEach(System.out::println);
                        System.out.println("0. Back");
                        managementCar(carDaoH2, companyDaoH2, userChoice());
                    }
                    break;
                case 2:
                    System.out.println("Enter the company name:");
                    companyDaoH2.createCompany(scanner.nextLine());
                    System.out.println("The company was created!\n");
                    break;
            }
        }
    }

    public static void managementCreateCustomer(CustomerDaoH2Impl customerDaoH2) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the customer name:");
        customerDaoH2.createCustomer(scanner.nextLine());
        System.out.println("The customer was added!\n");
    }

    public static void managementCustomerMenu(CustomerDaoH2Impl customerDaoH2,
                                              CompanyDaoH2Impl companyDaoH2, CarDaoH2Impl carDaoH2) {
        if(customerDaoH2.getAllCustomers().size() == 0) {
            System.out.println("The customer list is empty!\n");
            return;
        } else {
            System.out.println("Choose a customer:");
            customerDaoH2.getAllCustomers()
                    .stream()
                    .forEach(System.out::println);
            System.out.println("0. Back\n");
            Customer customer = customerDaoH2.getCustomer(userChoice());
            managementCustomer(customerDaoH2, companyDaoH2, carDaoH2, customer);
        }
    }

    public static void managementCustomer(CustomerDaoH2Impl customerDaoH2,
                                          CompanyDaoH2Impl companyDaoH2, CarDaoH2Impl carDaoH2, Customer customer) {
        for(;;) {
            MenuPrinter.customerMenu();
            switch (userChoice()) {
                case 0:
                    return;
                case 1:
                    if(companyDaoH2.getAllCompanies().size() == 0) {
                        System.out.println("The company list is empty!\n");
                    } else {
                        if (customer.getCarId() > 0) {
                            System.out.println("You've already rented a car!");
                            break;
                        } else {
                            System.out.println("Choose the company:");
                            companyDaoH2.getAllCompanies()
                                .stream()
                                .forEach(System.out::println);
                            System.out.println("0. Back");
                            int companyId = userChoice();
                            if (companyId == 0) {
                                System.out.println();
                                break;
                            }
                            Company company = companyDaoH2.getCompany(companyId);
                            managementRentCar(carDaoH2, customerDaoH2, company, customer);
                        }
                    }
                    break;
                case 2:
                    customer = customerDaoH2.getCustomer(customer.getId());
                    if (customer.getCarId() == 0) {
                        System.out.println("You didn't rent a car!\n");
                    } else {
                        returnCar(customerDaoH2, customer);
                    }
                    break;
                case 3:
                    customer = customerDaoH2.getCustomer(customer.getId());
                    if (customer.getCarId() == 0) {
                        System.out.println("You didn't rent a car!\n");
                    } else {
                        Car car = carDaoH2.getCar(customer.getCarId());
                        Company company = companyDaoH2.getCompany(car.getCompanyId());
                        System.out.println("Your rented car:");
                        System.out.println(car.getName());
                        System.out.println("Company:");
                        System.out.println(company.getName());
                        System.out.println();
                    }
            }
        }

    }

    private static void managementRentCar(CarDaoH2Impl carDaoH2,
                                          CustomerDaoH2Impl customerDaoH2,
                                          Company company, Customer customer) {
        List<Integer> rentedCarsId = customerDaoH2.getAllRentedCars();
        List<Car> carList = carDaoH2.getCars(company.getId())
                .stream()
                .filter((c) -> !rentedCarsId.contains(c.getId()))
                .collect(Collectors.toList());
        if (carList.size() == 0) {
            System.out.println("The car list is empty!\n");
            return;
        } else {

            System.out.println("Choose a car:");
            int id = 1;
            for (Car car : carList) {
                System.out.println(id + ". " + car.getName());
                id++;
            }
            System.out.println("0. Back");
            int carId = userChoice();
            if (carId == 0) {
                return;
            } else {

                if (carId > carList.size() + 1 || carId < 0) {
                    return;
                } else {
                    Car car = carList.get(carId - 1);
                    rentCar(customerDaoH2, car, customer);
                }

            }

        }
    }

    private static void rentCar(CustomerDaoH2Impl customerDaoH2, Car car, Customer customer) {
        customerDaoH2.rentCar(car.getId(), customer.getId());
        System.out.println("You rented '" + car.getName() + "'");
        System.out.println();
    }

    private static void returnCar(CustomerDaoH2Impl customerDaoH2, Customer customer) {
        customerDaoH2.returnCar(customer.getCarId(), customer.getId());
        System.out.println("You've returned a rented car!\n");
    }

    public static int userChoice() {
        return scanner.nextInt();
    }

}
