import edu.duke.*;
import org.apache.commons.csv.*;

import java.io.File;

public class BabyBirths {
    public static void printNames () {
        FileResource fr = new FileResource();
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            if (numBorn <= 100) {
                System.out.println("Name " + rec.get(0) +
                        " Gender " + rec.get(1) +
                        " Num Born " + rec.get(2));
            }
        }
    }

    public static void totalBirths (FileResource fr) {
        int totalBirths = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        int totalGirlsName = 0;
        int totalBoysName = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            if (rec.get(1).equals("M")) {
                totalBoys += numBorn;
                totalBoysName++;
            }
            else {
                totalGirls += numBorn;
                totalGirlsName++;
            }
        }
        System.out.println("total births = " + totalBirths);
        System.out.println("female girls = " + totalGirls);
        System.out.println("male boys = " + totalBoys);
        System.out.println("Total Boy's Names: " +totalBoysName);
        System.out.println("Total Girl's Names: " +totalGirlsName);
    }

    public void testTotalBirths () {
        //FileResource fr = new FileResource();
        FileResource fr = new FileResource();
        totalBirths(fr);
    }
    public static int getRank (int year, String name, String gender) {
        int rank = 0;
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser(false);
        for (CSVRecord rec : parser) {
            if (rec.get(1).equals(gender)) {
                rank++;
                if (rec.get(0).equals(name)) {
                    return rank;
                }
            }
        }
        return -1;
    }
    public static String getName (int year, int rank, String gender) {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser(false);
        int rankTemp = 0;
        for(CSVRecord rec : parser) {
            if (rec.get(1).equals(gender)) {
                rankTemp++;
                if (rankTemp == rank) {
                    return rec.get(0);
                }
            }
        }
        return "No Name";
    }
    public static int getRank2 (int year, String name, String gender,FileResource fr) {
        int rank = 0;
        CSVParser parser = fr.getCSVParser(false);
        for (CSVRecord rec : parser) {
            if (rec.get(1).equals(gender)) {
                rank++;
                if (rec.get(0).equals(name)) {
                    return rank;
                }
            }
        }
        return -1;
    }
    public static int yearOfHighestRank (String name, String gender) {
        int year = 0;
        int rank = -1;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVParser parser = fr.getCSVParser();
            String fileName = f.getName();
            int yearTemp = Integer.parseInt(fileName.replaceAll("[\\D]", ""));
            int currRank = getRank2(yearTemp, name, gender,fr);
            if (rank == -1) {
                rank = currRank;
            }
            if (currRank < rank && currRank != -1) {
                rank = currRank;
                year = yearTemp;
            }

        }
        return year;
    }
    public static double getAverageRank(String name, String gender) {
        double rankTemp = -1;
        double num = 0;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            String fileName = f.getName();
            int yearTemp = Integer.parseInt(fileName.replaceAll("[\\D]", ""));
            double currRank = getRank2(yearTemp, name, gender,fr);
            if (rankTemp == -1) {
                rankTemp = currRank;
                num++;
            }else {
                rankTemp = rankTemp + currRank;
                num++;
            }
        }
    return rankTemp / num;
    }
    public static void whatIsNameInYear(String name,int year,int newYear, String gender) {
        int currNameRank = getRank(year,name,gender);
        String newName = getName(newYear,currNameRank,gender);
        System.out.println(name+" born in " + year + " would be " + newName + " in " + newYear);
    }
    public static int getTotalBirthsRankedHigher(int year, String name, String gender) {
        FileResource fr = new FileResource();

        int sum = 0;
        for(CSVRecord record : fr.getCSVParser(false)){

            if(record.get(1).equals(gender)){

                if(record.get(0).equals(name))
                    return sum;

                sum += Integer.parseInt(record.get(2));

            }//end if record euqals gender condition;

        }//end for CSV record record;

        return sum;
    }
    public static void main(String[] args) {
        //FileResource fr = new FileResource();
        //System.out.println(getRank(1971,"Frank","M"));
        //System.out.println(getName(1982,450,"M"));
        //whatIsNameInYear("Owen", 1974,2014,"M");
        //System.out.println(yearOfHighestRank("Genevieve","F"));
        //System.out.println(getAverageRank("Susan","F"));
        System.out.println(getTotalBirthsRankedHigher(1990, "Emily", "F"));
        //totalBirths(fr);
    }
}
