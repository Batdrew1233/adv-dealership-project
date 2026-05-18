package com.pluralsight.contract;

import com.pluralsight.dealership.Vehicle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class ContractDataManager {

    public ArrayList<Contract> getContracts(){
        Contract contract = null;
        ArrayList<Contract> contracts = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("contracts.csv"))){
            String line;
            while ((line = bufferedReader.readLine()) != null){
                String[] fields = line.split("\\|");
                    String type = fields[0];
                    String date = fields[1];
                    String customerName = fields[2];
                    String customerEmail = fields[3];
                    int carId = Integer.parseInt(fields[4]);
                    int year = Integer.parseInt(fields[5]);
                    String make = fields [6];
                    String model = fields[7];
                    String vehicleType = fields[8];
                    String color = fields[9];
                    int odometer = Integer.parseInt(fields[10]);
                    double price = Double.parseDouble(fields[11]);
                    Vehicle vehicle = new Vehicle(carId, year, make, model, vehicleType, color, odometer, price);
                    if (type.equalsIgnoreCase("sale")){
                        double salesTaxAmount = Double.parseDouble(fields[12]);
                        double recordingFee = Double.parseDouble(fields[13]);
                        double processingFee = Double.parseDouble(fields[14]);
                        boolean finance = fields[16].equalsIgnoreCase("yes");
                        contract = new SalesContract(date, customerName, customerEmail, vehicle, salesTaxAmount, recordingFee, processingFee, finance);
                    } else if (type.equalsIgnoreCase("lease")) {
                        double expectedEndingValue = Double.parseDouble(fields[12]);
                        double leaseFee = Double.parseDouble(fields[13]);
                        contract = new LeaseContract(date, customerName, customerEmail, vehicle, expectedEndingValue, leaseFee);
                    }

            }
            }

        catch (Exception ex){
            System.out.println("Something went wrong.");
        }
        if(contract != null){
            contracts.add(contract);
        }
        return contracts;
    }

    public void saveContract(Contract contract){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("contracts.csv", true));

            Vehicle vehicle = contract.getVehicleSold();

            if(contract instanceof SalesContract sale){
                writer.write("SALE|" + contract.getDate()+ "|" + contract.getCustomerName() + "|" + contract.getCustomerEmail() + "|" +
                        vehicle.getVin() + "|" + vehicle.getYear() + "|" + vehicle.getMake() + "|" + vehicle.getModel() + "|" +
                        vehicle.getVehicleType() + "|" + vehicle.getColor() + "|" + vehicle.getOdometer() + "|" + vehicle.getPrice() + "|" +
                        sale.getSalesTaxAmount() + "|" + sale.getRecordingFee() + "|" + sale.getProcessingFee() + "|" + sale.getTotalPrice() + "|" +
                        sale.isFinanceOption() + "|" + sale.getMonthlyPayment());

            } else if (contract instanceof LeaseContract lease) {
                writer.write("LEASE|" + contract.getDate() + "|" + contract.getCustomerName() + "|" + contract.getCustomerEmail() + "|" +
                        vehicle.getVin() + "|" + vehicle.getYear() + "|" + vehicle.getMake() + "|" + vehicle.getModel() + "|" +
                        vehicle.getVehicleType() + "|" + vehicle.getColor() + "|" + vehicle.getOdometer() + "|" + vehicle.getPrice() + "|" +
                        lease.getExpectedEndingValue() + "|" + lease.getLeaseFee() + "|" + lease.getTotalPrice() + "|" + lease.getMonthlyPayment());
            }
            writer.newLine();
            writer.close();
        }
        catch (Exception ex){
            System.out.println("Something went wrong");
        }
    }
}
