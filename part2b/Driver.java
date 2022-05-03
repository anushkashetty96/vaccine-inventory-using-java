package part2.part2b;


import part2.part2a.Vaccine;
import part2.part2a.VaccineBrand;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Driver {

    private static int vaccineTargetIndex=0;
    private static Vaccine[] inventory;
    private static Scanner scanner = new Scanner(System.in);
    static int addVaccinesPasswordLimitCount = 0;
    static int updateVaccinesPasswordLimitCount = 0;

    public static void main(String[] args) {

        int maxVaccines;
        System.out.println("****Welcome to vaccine inventory system****");

        System.out.println("Please enter maximum number of vaccines your store can contain");
        maxVaccines = Integer.parseInt(scanner.nextLine());
        inventory = new Vaccine[maxVaccines];


        int mainMenuOption = 0;

        do {
            while (true) {
                displayMainMenu();
                mainMenuOption = Integer.parseInt(scanner.nextLine());
                if (mainMenuOption >= 1 && mainMenuOption <= 5)
                    break;
                else
                    continue;
            }

            switch (mainMenuOption) {
                case 1:
                    if (authenticate("addition")) {
                        if(vaccineTargetIndex<inventory.length)
                            addVaccines();
                        else
                            System.out.println("Inventory is full!\n");
                    }
                    break;
                case 2:
                    updateVaccinesPasswordLimitCount=0;
                    if(authenticate("updation"))
                    {
                        updateVaccines();
                    }
                    break;
                case 3:
                    searchAndDisplayVaccineByBrand();
                    break;
                case 4:
                    searchAndDisplayVaccineLessThanPrice();
                    break;
                case 5:
                    System.out.println("Thank you! Exiting application.....");
                    break;
            }
        }while (mainMenuOption!=5);

    }

    private static void searchAndDisplayVaccineLessThanPrice() {
        Double price;
        while(true)
        {
            try {
                System.out.println("Enter price to search");
                price = Double.parseDouble(scanner.nextLine());
                if(price>=0)
                    break;
                else
                    System.out.println("Please enter valid price");
            }catch (NumberFormatException e) {
                System.out.println("Please enter valid price");
            }
        }

        List<Vaccine> vaccinesLessThanPrice = findCheaperThan(price);
        if(vaccinesLessThanPrice.size()>0) {
            System.out.println("List of vaccines less than price " + price + " are: ");
            for (Vaccine vaccine : vaccinesLessThanPrice) {
                System.out.println(vaccine);
            }
        }
        else
            System.out.println("Sorry! Vaccines less than price "+price+" not found");

    }

    private static List<Vaccine> findCheaperThan(Double price) {
        List<Vaccine> vaccines = new ArrayList<>();
        for (Vaccine vaccine : inventory) {
            if(vaccine!=null && vaccine.getPrice()<price)
                vaccines.add(vaccine);
        }

        return vaccines;
    }

    private static void searchAndDisplayVaccineByBrand() {
        int brandId=0;
        while(true) {
            System.out.println("Choose vaccine brand : ");
            int brandCount = 1;
            for (VaccineBrand brand : VaccineBrand.values()) {
                System.out.println(brandCount++ + " " + brand);
            }
            brandId = Integer.parseInt(scanner.nextLine());
            if(brandId>=1 && brandId<=VaccineBrand.values().length)
                break;
            else
                System.out.println("Please choose valid option");
        }


        VaccineBrand brand = null;
        if (brandId == 1)
            brand = VaccineBrand.PFIZER;
        if (brandId == 2)
            brand = VaccineBrand.MODERNA;

        List<Vaccine> vaccinesByBrand = findVaccinesBy(brand);
        if(vaccinesByBrand.size()>0) {
            System.out.println("List of vaccines for brand " + brand + " are: ");
            for (Vaccine vaccine : vaccinesByBrand) {
                System.out.println(vaccine);
            }
        }
        else
            System.out.println("Sorry! Vaccines of brand "+brand+" not found");
    }

    private static void updateVaccines() {
        int index=-1;

        while(true)
        {
            System.out.println("Enter the vaccine index you want to update");
            index=Integer.parseInt(scanner.nextLine());
            if(index<=vaccineTargetIndex && index>=0)
                break;
            else
            {
                System.out.println("Please enter valid vaccine index (maximum is "+(vaccineTargetIndex-1)+")");
                continue;
            }

        }

        System.out.println(inventory[index]);

        int option=-1; //reset option

        while(option!=5)
        {
            System.out.println("\nWhat information would you like to change?\n"+
                    "1. Brand\n"+
            "2. Dose\n"+
            "3. Expiry\n"+
            "4. Price\n"+
            "5. Quit\n"+
            "Enter your choice >");

            option=Integer.parseInt(scanner.nextLine());
            if(option>=1 && option<=4)
            {
                switch (option)
                {
                    case 1:
                        System.out.println("Choose vaccine brand : ");
                        int brandCount = 1;
                        for (VaccineBrand brand : VaccineBrand.values()) {
                            System.out.println(brandCount++ + " " + brand);
                        }

                        int brandId = Integer.parseInt(scanner.nextLine());
                        VaccineBrand brand = null;
                        if (brandId == 1)
                            brand = VaccineBrand.PFIZER;
                        if (brandId == 2)
                            brand = VaccineBrand.MODERNA;
                        inventory[index].setBrand(brand);
                        System.out.println("Vaccine updated");
                        System.out.println(inventory[index]);
                        break;
                    case 2:
                        System.out.println("Enter dose");
                        double dose = Double.parseDouble(scanner.nextLine());
                        inventory[index].setDose(dose);
                        System.out.println("Vaccine updated");
                        System.out.println(inventory[index]);
                        break;
                    case 3:
                        System.out.println("Enter expiry date (yyyy-mm-dd)");
                        String expiry_date = scanner.nextLine();
                        inventory[index].setExpiry_date(expiry_date);
                        System.out.println("Vaccine updated");
                        System.out.println(inventory[index]);
                        break;
                    case 4:
                        System.out.println("Enter price");
                        double price = Double.parseDouble(scanner.nextLine());
                        inventory[index].setPrice(price);
                        System.out.println("Vaccine updated");
                        System.out.println(inventory[index]);
                        break;
                }
            }
            else if(option>5)
            {
                System.out.println("Please enter valid option\n");
            }
        }
    }


    private static boolean authenticate(String operation)
    {
        String valueEntered;
        if(operation.equals("addition")) {
            while (true) {
                System.out.println("Please enter password");
                valueEntered = scanner.nextLine();
                if (valueEntered.equals(Config.password))
                    return true;
                else {
                    addVaccinesPasswordLimitCount++;
                    if (addVaccinesPasswordLimitCount == 12) {
                        System.out.println("Program detected suspicious activities and will terminate immediately!");
                        System.exit(0);
                    }
                    if (addVaccinesPasswordLimitCount != 0 && addVaccinesPasswordLimitCount % 3 == 0) {
                        return false;
                    }
                }
            }
        }
        else
        {
            while (true) {
                System.out.println("Please enter password");
                valueEntered = scanner.nextLine();
                if (valueEntered.equals(Config.password))
                    return true;
                else {
                    updateVaccinesPasswordLimitCount++;
                    if (updateVaccinesPasswordLimitCount != 0 && updateVaccinesPasswordLimitCount % 3 == 0) {
                        return false;
                    }
                }
            }
        }
    }

    private static void addVaccines()
    {
        int availableSpace = inventory.length-vaccineTargetIndex;
        int option=0;
        while(true)
        {
            System.out.println("How many vaccines do you want to enter?");
            option=Integer.parseInt(scanner.nextLine());
            if(option<=availableSpace)
                break;
            else {
                System.out.println("Only "+availableSpace+" spaces available");
            }
        }

        while(option-->0) {

            System.out.println("Choose vaccine brand : ");
            int brandCount = 1;
            for (VaccineBrand brand : VaccineBrand.values()) {
                System.out.println(brandCount++ + " " + brand);
            }

            int brandId = Integer.parseInt(scanner.nextLine());
            VaccineBrand brand = null;
            if (brandId == 1)
                brand = VaccineBrand.PFIZER;
            if (brandId == 2)
                brand = VaccineBrand.MODERNA;

            System.out.println("Enter dose");
            double dose = Double.parseDouble(scanner.nextLine());

            System.out.println("Enter expiry date (yyyy-mm-dd)");
            String expiry_date = scanner.nextLine();

            System.out.println("Enter price");
            double price = Double.parseDouble(scanner.nextLine());

            inventory[vaccineTargetIndex++] = new Vaccine(brand, dose, expiry_date, price);

            System.out.println("vaccine added!"); //Todo add vaccines
            System.out.println(inventory[vaccineTargetIndex - 1]);
        }
    }

    private static void displayMainMenu()
    {
        System.out.println(
                "\nWhat do you want to do?\n"+
        "1. Enter new vaccines (password required)\n"+
        "2. Change information of a vaccine (password required)\n"+
        "3. Display all vaccines by a specific brand\n"+
        "4. Display all vaccines under a certain a price.\n"+
        "5. Quit\n"+
        "Please enter your choice >"
        );
    }

    private static List<Vaccine> findVaccinesBy(VaccineBrand brand)
    {
        List<Vaccine> vaccines = new ArrayList<>();
        for (Vaccine vaccine : inventory) {
            if(vaccine!=null && vaccine.getBrand().equals(brand))
                vaccines.add(vaccine);
        }

        return vaccines;
    }


}
