package stocks_management.main;

import database.config.SetupData;
import stocks_management.category.Category;
import stocks_management.distributor.Distributor;
import stocks_management.product.*;
import stocks_management.product.filterable.PriceFilter;
import stocks_management.services.*;
import stocks_management.transaction.Transaction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    private static void categoriesCRUD() {

        ReadingService readingService = ReadingService.getInstance();
        CategoryService categoryService = CategoryService.getInstance();
        StockService stockService = StockService.getInstance();

        readingService.readCategories();

        try {
            File outputFile = new File("./resources/database_output/categories.txt");
            outputFile.createNewFile();
        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }

        try {
            FileWriter writer = new FileWriter("./resources/database_output/categories.txt");

            List<Category> categories = categoryService.getAll();
            if (categories.isEmpty()) {
                for (Category category : stockService.getCategories()) {
                    categoryService.add(category);
                }
            }

            categories = categoryService.getAll();
            writer.write("Initial categories:\n");
            for (Category category : categories) {
                writer.write(category + "\n");
            }

            Category newCategory = new Category("New category");
            categoryService.add(newCategory);
            categories = categoryService.getAll();
            writer.write("\nAdded a new category:\n");
            for (Category category : categories) {
                writer.write(category + "\n");
            }

            categoryService.changeName(newCategory, "New category edited");
            categories = categoryService.getAll();
            Category categoryToDelete = null;
            writer.write("\nEdited a category:\n");
            for (Category category : categories) {
                if (category.getCategoryName().equals("New category edited")) {
                    categoryToDelete = category;
                }
                writer.write(category + "\n");
            }

            categoryService.delete(categoryToDelete);
            categories = categoryService.getAll();
            writer.write("\nDeleted a category:\n");
            for (Category category : categories) {
                writer.write(category + "\n");
            }

            writer.close();

        } catch(IOException exception) {
            System.out.println("EXCEPTION: "  + exception.getMessage());
        }
    }

    private static void distributorsCRUD() {

        ReadingService readingService = ReadingService.getInstance();
        DistributorService distributorService = DistributorService.getInstance();
        StockService stockService = StockService.getInstance();

        readingService.readDistributors();

        try {
            File outputFile = new File("./resources/database_output/distributors.txt");
            outputFile.createNewFile();
        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }

        try {
            FileWriter writer = new FileWriter("./resources/database_output/distributors.txt");

            List<Distributor> distributors = distributorService.getAll();
            if (distributors.isEmpty()) {
                for (Distributor distributor : stockService.getDistributors()) {
                    distributorService.add(distributor);
                }
            }

            distributors = distributorService.getAll();
            writer.write("Initial distributors:\n");
            for (Distributor distributor : distributors) {
                writer.write(distributor + "\n");
            }

            Distributor newDistributor = new Distributor("New distributor", "Romania");
            distributorService.add(newDistributor);
            distributors = distributorService.getAll();
            writer.write("\nAdded a new distributor:\n");
            for (Distributor distributor : distributors) {
                writer.write(distributor + "\n");
            }

            distributorService.update(newDistributor, "New distributor edited", "Japan");
            distributors = distributorService.getAll();
            Distributor distributorToDelete = null;
            writer.write("\nEdited a distributor:\n");
            for (Distributor distributor : distributors) {
                if (distributor.getDistributorName().equals("New distributor edited")) {
                    distributorToDelete = distributor;
                }
                writer.write(distributor + "\n");
            }

            distributorService.delete(distributorToDelete);
            distributors = distributorService.getAll();
            writer.write("\nDeleted a distributor:\n");
            for (Distributor distributor : distributors) {
                writer.write(distributor + "\n");
            }

            writer.close();

        } catch(IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }

    private static void audioSpeakersCRUD() {

        ReadingService readingService = ReadingService.getInstance();
        AudioSpeakerService audioSpeakerService = AudioSpeakerService.getInstance();
        StockService stockService = StockService.getInstance();

        readingService.readAudioSpeakers();

        try {
            File outputFile = new File("./resources/database_output/audio_speakers.txt");
            outputFile.createNewFile();
        } catch(IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }

        try {
            FileWriter writer = new FileWriter("./resources/database_output/audio_speakers.txt");

            List <AudioSpeaker> audioSpeakers = audioSpeakerService.getAll();
            if(audioSpeakers.isEmpty()) {
                for(AudioSpeaker audioSpeaker : stockService.getAudioSpeakers()) {
                    audioSpeakerService.add(audioSpeaker);
                }
            }

            audioSpeakers = audioSpeakerService.getAll();
            writer.write("Initial audio speakers:\n");
            for(AudioSpeaker audioSpeaker : audioSpeakers) {
                writer.write(audioSpeaker + "\n");
            }

            AudioSpeaker newAudioSpeaker = stockService.generateAudioSpeaker();
            audioSpeakerService.add(newAudioSpeaker);
            audioSpeakers = audioSpeakerService.getAll();
            writer.write("\nAdded a new audio speaker:\n");
            for(AudioSpeaker audioSpeaker : audioSpeakers) {
                writer.write(audioSpeaker + "\n");
            }

            audioSpeakerService.update(newAudioSpeaker, 400.0, 40000);
            audioSpeakers = audioSpeakerService.getAll();
            AudioSpeaker audioSpeakerToDelete = null;
            writer.write("\nEdited an audio speaker:\n");
            for(AudioSpeaker audioSpeaker : audioSpeakers) {
                if(audioSpeaker.getProductName().equals(newAudioSpeaker.getProductName())) {
                    audioSpeakerToDelete = audioSpeaker;
                }
                writer.write(audioSpeaker + "\n");
            }

            audioSpeakerService.delete(audioSpeakerToDelete);
            audioSpeakers = audioSpeakerService.getAll();
            writer.write("\nDeleted an audio speaker:\n");
            for(AudioSpeaker audioSpeaker : audioSpeakers) {
                writer.write(audioSpeaker + "\n");
            }

            writer.close();

        } catch(IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }

    private static void audioSystemsCRUD() {

        ReadingService readingService = ReadingService.getInstance();
        AudioSystemService audioSystemService = AudioSystemService.getInstance();
        StockService stockService = StockService.getInstance();

        readingService.readAudioSystems();

        try {
            File outputFile = new File("./resources/database_output/audio_systems.txt");
            outputFile.createNewFile();
        } catch(IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }

        try {
            FileWriter writer = new FileWriter("./resources/database_output/audio_systems.txt");

            List<AudioSystem> audioSystems = audioSystemService.getAll();
            if(audioSystems.isEmpty()) {
                for(AudioSystem audioSystem : stockService.getAudioSystems()) {
                    audioSystemService.add(audioSystem);
                }
            }

            audioSystems = audioSystemService.getAll();
            writer.write("Initial audio systems:\n");
            for(AudioSystem audioSystem : audioSystems) {
                writer.write(audioSystem + "\n");
            }

            AudioSystem newAudioSystem = stockService.generateAudioSystem();
            audioSystemService.add(newAudioSystem);
            audioSystems = audioSystemService.getAll();
            writer.write("\nAdded a new audio system:\n");
            for(AudioSystem audioSystem : audioSystems) {
                writer.write(audioSystem + "\n");
            }

            audioSystemService.update(newAudioSystem, 1000.0, 200);
            audioSystems = audioSystemService.getAll();
            AudioSystem audioSystemToDelete = null;
            writer.write("\nEdited an audio system:\n");
            for(AudioSystem audioSystem : audioSystems) {
                if(audioSystem.getProductName().equals(newAudioSystem.getProductName())) {
                    audioSystemToDelete = audioSystem;
                }
                writer.write(audioSystem + "\n");
            }

            audioSystemService.delete(audioSystemToDelete);
            audioSystems = audioSystemService.getAll();
            writer.write("\nDeleted an audio system:\n");
            for(AudioSystem audioSystem : audioSystems) {
                writer.write(audioSystem + "\n");
            }

            writer.close();

        } catch(IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }

    private static void fridgesCRUD() {

        ReadingService readingService = ReadingService.getInstance();
        FridgeService fridgeService = FridgeService.getInstance();
        StockService stockService = StockService.getInstance();

        readingService.readFridges();

        try {
            File outputFile = new File("./resources/database_output/fridges.txt");
            outputFile.createNewFile();
        } catch(IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }

        try {
            FileWriter writer = new FileWriter("./resources/database_output/fridges.txt");

            List<Fridge> fridges = fridgeService.getAll();
            if(fridges.isEmpty()) {
                for(Fridge fridge : stockService.getFridges()) {
                    fridgeService.add(fridge);
                }
            }

            fridges = fridgeService.getAll();
            writer.write("Initial fridges:\n");
            for(Fridge fridge : fridges) {
                writer.write(fridge + "\n");
            }

            Fridge newFridge = stockService.generateFridge();
            fridgeService.add(newFridge);
            fridges = fridgeService.getAll();
            writer.write("\nAdded a new fridge:\n");
            for(Fridge fridge : fridges) {
                writer.write(fridge + "\n");
            }

            fridgeService.update(newFridge, 2000.0, 600);
            fridges = fridgeService.getAll();
            Fridge fridgeToDelete = null;
            writer.write("\nEdited a fridge:\n");
            for(Fridge fridge : fridges) {
                if(fridge.getProductName().equals(newFridge.getProductName())) {
                    fridgeToDelete = fridge;
                }
                writer.write(fridge + "\n");
            }

            fridgeService.delete(fridgeToDelete);
            fridges = fridgeService.getAll();
            writer.write("\nDeleted a fridge:\n");
            for(Fridge fridge : fridges) {
                writer.write(fridge + "\n");
            }

            writer.close();

        } catch(IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }

    private static void gasCookersCRUD() {

        ReadingService readingService = ReadingService.getInstance();
        GasCookerService gasCookerService = GasCookerService.getInstance();
        StockService stockService = StockService.getInstance();

        readingService.readGasCookers();

        try {
            File outputFile = new File("./resources/database_output/gas_cookers.txt");
            outputFile.createNewFile();
        } catch(IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }

        try {
            FileWriter writer = new FileWriter("./resources/database_output/gas_cookers.txt");

            List<GasCooker> gasCookers = gasCookerService.getAll();
            if(gasCookers.isEmpty()) {
                for(GasCooker gasCooker : stockService.getGasCookers()) {
                    gasCookerService.add(gasCooker);
                }
            }

            gasCookers = gasCookerService.getAll();
            writer.write("Initial gas cookers:\n");
            for(GasCooker gasCooker : gasCookers) {
                writer.write(gasCooker + "\n");
            }

            GasCooker newGasCooker = stockService.generateGasCooker();
            gasCookerService.add(newGasCooker);
            gasCookers = gasCookerService.getAll();
            writer.write("\nAdded a new gas cooker:\n");
            for(GasCooker gasCooker : gasCookers) {
                writer.write(gasCooker + "\n");
            }

            gasCookerService.update(newGasCooker, 900.0, 10);
            gasCookers = gasCookerService.getAll();
            GasCooker gasCookerToDelete = null;
            writer.write("\nEdited a gas cooker:\n");
            for(GasCooker gasCooker : gasCookers) {
                if(gasCooker.getProductName().equals(newGasCooker.getProductName())) {
                    gasCookerToDelete = gasCooker;
                }
                writer.write(gasCooker + "\n");
            }

            gasCookerService.delete(gasCookerToDelete);
            gasCookers = gasCookerService.getAll();
            writer.write("\nDeleted a gas cooker:\n");
            for(GasCooker gasCooker : gasCookers) {
                writer.write(gasCooker + "\n");
            }

            writer.close();

        } catch(IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }

    private static void headphonesCRUD() {

        ReadingService readingService = ReadingService.getInstance();
        HeadphonesService headphonesService = HeadphonesService.getInstance();
        StockService stockService = StockService.getInstance();

        readingService.readHeadphones();

        try {
            File outputFile = new File("./resources/database_output/headphones.txt");
            outputFile.createNewFile();
        } catch(IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }

        try {
            FileWriter writer = new FileWriter("./resources/database_output/headphones.txt");

            List<Headphones> headphones = headphonesService.getAll();
            if(headphones.isEmpty()) {
                for(Headphones headphones1 : stockService.getHeadphones()) {
                    headphonesService.add(headphones1);
                }
            }

            headphones = headphonesService.getAll();
            writer.write("Initial headphones:\n");
            for(Headphones headphones1 : headphones) {
                writer.write(headphones1 + "\n");
            }

            Headphones newHeadphones = stockService.generateHeadphones();
            headphonesService.add(newHeadphones);
            headphones = headphonesService.getAll();
            writer.write("\nAdded new headphones:\n");
            for(Headphones headphones1 : headphones) {
                writer.write(headphones1 + "\n");
            }

            headphonesService.update(newHeadphones, 300.0, 1000);
            headphones = headphonesService.getAll();
            Headphones headphonesToDelete = null;
            writer.write("\nEdited headphones:\n");
            for(Headphones headphones1 : headphones) {
                if(headphones1.getProductName().equals(newHeadphones.getProductName())) {
                    headphonesToDelete = headphones1;
                }
                writer.write(headphones1 + "\n");
            }

            headphonesService.delete(headphonesToDelete);
            headphones = headphonesService.getAll();
            writer.write("\nDeleted headphones:\n");
            for(Headphones headphones1 : headphones) {
                writer.write(headphones1 + "\n");
            }

            writer.close();

        } catch(IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }

    private static void laptopsCRUD() {

        ReadingService readingService = ReadingService.getInstance();
        LaptopService laptopService = LaptopService.getInstance();
        StockService stockService = StockService.getInstance();

        readingService.readLaptops();

        try {
            File outputFile = new File("./resources/database_output/laptops.txt");
            outputFile.createNewFile();
        } catch(IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }

        try {
            FileWriter writer = new FileWriter("./resources/database_output/laptops.txt");

            List<Laptop> laptops = laptopService.getAll();
            if(laptops.isEmpty()) {
                for(Laptop laptop : stockService.getLaptops()) {
                    laptopService.add(laptop);
                }
            }

            laptops = laptopService.getAll();
            writer.write("Initial laptops:\n");
            for(Laptop laptop : laptops) {
                writer.write(laptop + "\n");
            }

            Laptop newLaptop = stockService.generateLaptop();
            laptopService.add(newLaptop);
            laptops = laptopService.getAll();
            writer.write("\nAdded a new laptop:\n");
            for(Laptop laptop : laptops) {
                writer.write(laptop + "\n");
            }

            laptopService.update(newLaptop, 6000.0, 50);
            laptops = laptopService.getAll();
            Laptop laptopToDelete = null;
            writer.write("\nEdited a laptop:\n");
            for(Laptop laptop : laptops) {
                if(laptop.getProductName().equals(newLaptop.getProductName())) {
                    laptopToDelete = laptop;
                }
                writer.write(laptop + "\n");
            }

            laptopService.delete(laptopToDelete);
            laptops = laptopService.getAll();
            writer.write("\nDeleted a laptop:\n");
            for(Laptop laptop : laptops) {
                writer.write(laptop + "\n");
            }

            writer.close();

        } catch(IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }

    private static void mobilePhonesCRUD() {

        ReadingService readingService = ReadingService.getInstance();
        MobilePhoneService mobilePhoneService = MobilePhoneService.getInstance();
        StockService stockService = StockService.getInstance();

        readingService.readMobilePhones();

        try {
            File outputFile = new File("./resources/database_output/mobile_phones.txt");
            outputFile.createNewFile();
        } catch(IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }

        try {
            FileWriter writer = new FileWriter("./resources/database_output/mobile_phones.txt");

            List<MobilePhone> mobilePhones = mobilePhoneService.getAll();
            if(mobilePhones.isEmpty()) {
                for(MobilePhone mobilePhone : stockService.getMobilePhones()) {
                    mobilePhoneService.add(mobilePhone);
                }
            }

            mobilePhones = mobilePhoneService.getAll();
            writer.write("Initial mobile phones:\n");
            for(MobilePhone mobilePhone : mobilePhones) {
                writer.write(mobilePhone + "\n");
            }

            MobilePhone newMobilePhone = stockService.generateMobilePhone();
            mobilePhoneService.add(newMobilePhone);
            mobilePhones = mobilePhoneService.getAll();
            writer.write("\nAdded a new mobile phone:\n");
            for(MobilePhone mobilePhone : mobilePhones) {
                writer.write(mobilePhone + "\n");
            }

            mobilePhoneService.update(newMobilePhone, 4000.0, 500);
            mobilePhones = mobilePhoneService.getAll();
            MobilePhone mobilePhoneToDelete = null;
            writer.write("\nEdited a mobile phone:\n");
            for(MobilePhone mobilePhone : mobilePhones) {
                if(mobilePhone.getProductName().equals(newMobilePhone.getProductName())) {
                    mobilePhoneToDelete = mobilePhone;
                }
                writer.write(mobilePhone + "\n");
            }

            mobilePhoneService.delete(mobilePhoneToDelete);
            mobilePhones = mobilePhoneService.getAll();
            writer.write("\nDeleted a mobile phone:\n");
            for(MobilePhone mobilePhone : mobilePhones) {
                writer.write(mobilePhone + "\n");
            }

            writer.close();

        } catch(IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }

    private static void mousesCRUD() {

        ReadingService readingService = ReadingService.getInstance();
        MouseService mouseService = MouseService.getInstance();
        StockService stockService = StockService.getInstance();

        readingService.readMouses();

        try {
            File outputFile = new File("./resources/database_output/mouses.txt");
            outputFile.createNewFile();
        } catch(IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }

        try {
            FileWriter writer = new FileWriter("./resources/database_output/mouses.txt");

            List<Mouse> mouses = mouseService.getAll();
            if(mouses.isEmpty()) {
                for(Mouse mouse : stockService.getMouses()) {
                    mouseService.add(mouse);
                }
            }

            mouses = mouseService.getAll();
            writer.write("Initial mouses:\n");
            for(Mouse mouse : mouses) {
                writer.write(mouse + "\n");
            }

            Mouse newMouse = stockService.generateMouse();
            mouseService.add(newMouse);
            mouses = mouseService.getAll();
            writer.write("\nAdded a new mouse:\n");
            for(Mouse mouse : mouses) {
                writer.write(mouse + "\n");
            }

            mouseService.update(newMouse, 90.0, 55);
            mouses = mouseService.getAll();
            Mouse mouseToDelete = null;
            writer.write("\nEdited a mouse:\n");
            for(Mouse mouse : mouses) {
                if(mouse.getProductName().equals(newMouse.getProductName())) {
                    mouseToDelete = mouse;
                }
                writer.write(mouse + "\n");
            }

            mouseService.delete(mouseToDelete);
            mouses = mouseService.getAll();
            writer.write("\nDeleted a mouse:\n");
            for(Mouse mouse : mouses) {
                writer.write(mouse + "\n");
            }

            writer.close();

        } catch(IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }

    private static void powerBanksCRUD() {

        ReadingService readingService = ReadingService.getInstance();
        PowerBankService powerBankService = PowerBankService.getInstance();
        StockService stockService = StockService.getInstance();

        readingService.readPowerBanks();

        try {
            File outputFile = new File("./resources/database_output/power_banks.txt");
            outputFile.createNewFile();
        } catch(IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }

        try {
            FileWriter writer = new FileWriter("./resources/database_output/power_banks.txt");

            List<PowerBank> powerBanks = powerBankService.getAll();
            if(powerBanks.isEmpty()) {
                for(PowerBank powerBank : stockService.getPowerBanks()) {
                    powerBankService.add(powerBank);
                }
            }

            powerBanks = powerBankService.getAll();
            writer.write("Initial power banks:\n");
            for(PowerBank powerBank : powerBanks) {
                writer.write(powerBank + "\n");
            }

            PowerBank newPowerBank = stockService.generatePowerBank();
            powerBankService.add(newPowerBank);
            powerBanks = powerBankService.getAll();
            writer.write("\nAdded a new power bank:\n");
            for(PowerBank powerBank : powerBanks) {
                writer.write(powerBank + "\n");
            }

            powerBankService.update(newPowerBank, 300.0, 10);
            powerBanks = powerBankService.getAll();
            PowerBank powerBankToDelete = null;
            writer.write("\nEdited a power bank:\n");
            for(PowerBank powerBank : powerBanks) {
                if(powerBank.getProductName().equals(powerBank.getProductName())) {
                    powerBankToDelete = powerBank;
                }
                writer.write(powerBank + "\n");
            }

            powerBankService.delete(powerBankToDelete);
            powerBanks = powerBankService.getAll();
            writer.write("\nDeleted a power bank:\n");
            for(PowerBank powerBank : powerBanks) {
                writer.write(powerBank + "\n");
            }

            writer.close();

        } catch(IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }

    private static void smartwatchesCRUD() {

        ReadingService readingService = ReadingService.getInstance();
        SmartwatchService smartwatchService = SmartwatchService.getInstance();
        StockService stockService = StockService.getInstance();

        readingService.readSmartwatches();

        try {
            File outputFile = new File("./resources/database_output/smartwatches.txt");
            outputFile.createNewFile();
        } catch(IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }

        try {
            FileWriter writer = new FileWriter("./resources/database_output/smartwatches.txt");

            List<Smartwatch> smartwatches = smartwatchService.getAll();
            if(smartwatches.isEmpty()) {
                for(Smartwatch smartwatch : stockService.getSmartwatches()) {
                    smartwatchService.add(smartwatch);
                }
            }

            smartwatches = smartwatchService.getAll();
            writer.write("Initial smartwatches:\n");
            for(Smartwatch smartwatch : smartwatches) {
                writer.write(smartwatch + "\n");
            }

            Smartwatch newSmartwatch = stockService.generateSmartwatch();
            smartwatchService.add(newSmartwatch);
            smartwatches = smartwatchService.getAll();
            writer.write("\nAdded a new smartwatch:\n");
            for(Smartwatch smartwatch : smartwatches) {
                writer.write(smartwatch + "\n");
            }

            smartwatchService.update(newSmartwatch, 1000.0, 70);
            smartwatches = smartwatchService.getAll();
            Smartwatch smartwatchToDelete = null;
            writer.write("\nEdited a smartwatch:\n");
            for(Smartwatch smartwatch : smartwatches) {
                if(smartwatch.getProductName().equals(newSmartwatch.getProductName())) {
                    smartwatchToDelete = smartwatch;
                }
                writer.write(smartwatch + "\n");
            }

            smartwatchService.delete(smartwatchToDelete);
            smartwatches = smartwatchService.getAll();
            writer.write("\nDeleted a smartwatch:\n");
            for(Smartwatch smartwatch : smartwatches) {
                writer.write(smartwatch + "\n");
            }

            writer.close();

        } catch(IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }

    private static void tvsCRUD() {

        ReadingService readingService = ReadingService.getInstance();
        TVService tvService = TVService.getInstance();
        StockService stockService = StockService.getInstance();

        readingService.readTVs();

        try {
            File outputFile = new File("./resources/database_output/tvs.txt");
            outputFile.createNewFile();
        } catch(IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }

        try {
            FileWriter writer = new FileWriter("./resources/database_output/tvs.txt");

            List<TV> tvs = tvService.getAll();
            if(tvs.isEmpty()) {
                for(TV tv : stockService.getTVs()) {
                    tvService.add(tv);
                }
            }

            tvs = tvService.getAll();
            writer.write("Initial tvs:\n");
            for(TV tv : tvs) {
                writer.write(tv + "\n");
            }

            TV newTV = stockService.generateTV();
            tvService.add(newTV);
            tvs = tvService.getAll();
            writer.write("\nAdded a new tv:\n");
            for(TV tv : tvs) {
                writer.write(tv + "\n");
            }

            tvService.update(newTV, 1000.0, 70);
            tvs = tvService.getAll();
            TV tvToDelete = null;
            writer.write("\nEdited a tv:\n");
            for(TV tv : tvs) {
                if(tv.getProductName().equals(newTV.getProductName())) {
                    tvToDelete = tv;
                }
                writer.write(tv + "\n");
            }

            tvService.delete(tvToDelete);
            tvs = tvService.getAll();
            writer.write("\nDeleted a tv:\n");
            for(TV tv : tvs) {
                writer.write(tv + "\n");
            }

            writer.close();

        } catch(IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {

        AuditService auditService = AuditService.getInstance();
        StockService stockService = StockService.getInstance();
        CategoryService categoryService = CategoryService.getInstance();
        DistributorService distributorService = DistributorService.getInstance();
        TransactionService transactionService = TransactionService.getInstance();
        SetupData setupData = new SetupData();

        ReadingService readingService = ReadingService.getInstance();
        WritingService writingService = WritingService.getInstance();

        setupData.setup();

        categoriesCRUD();
        distributorsCRUD();
        audioSpeakersCRUD();
        audioSystemsCRUD();
        fridgesCRUD();
        gasCookersCRUD();
        headphonesCRUD();
        laptopsCRUD();
        mobilePhonesCRUD();
        mousesCRUD();
        powerBanksCRUD();
        smartwatchesCRUD();
        tvsCRUD();

        List<Category> categories = stockService.getCategories();
        Collections.sort(categories);
        List<Distributor> distributors = new ArrayList<>(stockService.getDistributors());

        writingService.writeCategories(categories);
        writingService.writeDistributors(distributors);


        List<Product> products = stockService.getStock();

        List<AudioSpeaker> audioSpeakers = new ArrayList<>();
        List<AudioSystem> audioSystems = new ArrayList<>();
        List<Fridge> fridges = new ArrayList<>();
        List<GasCooker> gasCookers = new ArrayList<>();
        List<Headphones> headphones = new ArrayList<>();
        List<Laptop> laptops = new ArrayList<>();
        List<MobilePhone> mobilePhones = new ArrayList<>();
        List<Mouse> mouses = new ArrayList<>();
        List<PowerBank> powerBanks = new ArrayList<>();
        List<Smartwatch> smartwatches = new ArrayList<>();
        List<TV> tvs = new ArrayList<>();

        Collections.sort(products);
        for(Product product : products) {
            if(product instanceof AudioSpeaker) {
                audioSpeakers.add((AudioSpeaker) product);
            } else if(product instanceof AudioSystem) {
                audioSystems.add((AudioSystem) product);
            } else if(product instanceof Fridge) {
                fridges.add((Fridge) product);
            } else if(product instanceof GasCooker) {
                gasCookers.add((GasCooker) product);
            } else if(product instanceof Headphones) {
                headphones.add((Headphones) product);
            } else if(product instanceof Laptop) {
                laptops.add((Laptop) product);
            } else if(product instanceof MobilePhone) {
                mobilePhones.add((MobilePhone) product);
            } else if(product instanceof Mouse) {
                mouses.add((Mouse) product);
            } else if(product instanceof PowerBank) {
                powerBanks.add((PowerBank) product);
            } else if(product instanceof Smartwatch) {
                smartwatches.add((Smartwatch) product);
            } else {
                tvs.add((TV) product);
            }
        }

        writingService.writeAudioSpeakers(audioSpeakers);
        writingService.writeAudioSystems(audioSystems);
        writingService.writeFridges(fridges);
        writingService.writeGasCookers(gasCookers);
        writingService.writeHeadphones(headphones);
        writingService.writeLaptops(laptops);
        writingService.writeMobilePhones(mobilePhones);
        writingService.writeMouses(mouses);
        writingService.writePowerBanks(powerBanks);
        writingService.writeSmartwatches(smartwatches);
        writingService.writeTVs(tvs);


        // show description for each product
        for(Product product : products) {
            product.showDescription();
        }

        // remove products from stock
        System.out.println(products.get(0));
        stockService.removeProduct(products.get(0));


        // modify price on category
        System.out.println();
        System.out.println();
        Category category = stockService.findCategoryByName("IT");
        Product[] productsIT = category.getProducts();
        for(Product product : productsIT) {
            System.out.println(product.getProductName() + "  " + product.getPrice());
        }
        System.out.println();
        System.out.println();
        stockService.modifyPrice(category, 0.2);

        // show products in category by price(the sorting can also be done by name or by distributor)
        category.showProductsByPrice();
        System.out.println();
        System.out.println();

        // modify price on product
        products = stockService.getStock();
        Product maxProduct = products.get(0);
        double maxPrice = 0.0;

        for(Product product : products) {
            if(product.getPrice() > maxPrice) {
                maxPrice = product.getPrice();
                maxProduct = product;
            }
        }

        System.out.println("The product with maximum price is: " + maxProduct.getProductDistributor().getDistributorName() +
                " " + maxProduct.getProductName() + ", price: " + maxPrice);
        stockService.modifyPrice(maxProduct, -0.1);
        System.out.println("The new price for " + maxProduct.getProductDistributor().getDistributorName() +
                " " + maxProduct.getProductName() + " is: " + maxProduct.getPrice());
        System.out.println();
        System.out.println();

        // show information about distributors
        for(Distributor distributor : distributors) {
            distributorService.showInformationAboutDistributor(distributor);
        }
        System.out.println();
        System.out.println();

        // apply promotion for all laptops -> the cheapest mouse
        products = stockService.getStock();
        Mouse mouse = null;
        double minPrice = mouses.get(0).getPrice();

        for(Product product : products) {

           if(product instanceof Mouse) {
                if(product.getPrice() < minPrice) {
                    minPrice = product.getPrice();
                    mouse = (Mouse) product;
                }
            }
        }
        for(Laptop laptop : laptops) {
            laptop.setPromotion(mouse);
        }

        stockService.showPromotionalProducts();
        System.out.println();
        System.out.println();


        // transactions
        Transaction transactionOne = new Transaction();
        Transaction transactionTwo = new Transaction();

        /// add products in transactions
        Random random = new Random();
        double price = random.nextDouble() * 20000.0;

        // filter products by price
        Product[] productsTransactionOne = stockService.filterLess(new PriceFilter(), price);
        for(Product product : productsTransactionOne) {
            transactionService.addProductToTransaction(transactionOne, product);
        }
        transactionService.closeTransaction(transactionOne);
        transactionService.showTransaction(transactionOne);
        System.out.println();
        System.out.println();

        Product last = null;
        price = random.nextDouble() * 10000.0;
        Product[] productsTransactionTwo = stockService.filterGreater(new PriceFilter(), price);
        for(Product product : productsTransactionTwo) {
            transactionService.addProductToTransaction(transactionTwo, product);
            last = product;
        }
        transactionService.showTransaction(transactionTwo);
        System.out.println();
        System.out.println();

        // remove a product from transaction
        transactionService.removeProductFromTransaction(transactionTwo, last);
        transactionService.closeTransaction(transactionTwo);
        // transaction is closed, so it won't be added
        transactionService.addProductToTransaction(transactionTwo, last);

        transactionService.showTransaction(transactionTwo);
        System.out.println();
        System.out.println();

        // it should return the sum of all transactions
        stockService.showTotalIncome();

        auditService.close();
    }
}
