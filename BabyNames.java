import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;
/**
 * Write a description of BabyNames here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BabyNames {
    public void totalBirths(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser(false);
        int countGirlsNames = 0;
        int countBoysNames = 0;
        int countNames = 0;
        for (CSVRecord record : parser){
            if (record.get(1).equals("M")) {
            countBoysNames +=1;
            countNames +=1;
            }
            else {
            countGirlsNames += 1;
            countNames +=1;
            }
        }
        
        System.out.println("boysNames : " + countBoysNames + "; girlsNames: "
                                        + countGirlsNames + "; totalNames: " + countNames);
    }
    
    public int getTotalBirthsRankedHigher(int  year, String name, String gender){
        String fn = "us_babynames\\us_babynames_by_year\\yob"+year+".csv";
        FileResource fr = new  FileResource(fn);
        
        int totalBirths = 0;
        int rank = getRank(year, name, gender);
        for (int i = 1; i <rank; i++){
            String higherName = getName(year, i, gender);
            CSVParser parser = fr.getCSVParser(false);
            for (CSVRecord record : parser){
                if(record.get(1).equals(gender)&&record.get(0).equals(higherName)){
                    String sBirths =  record.get(2); 
                    int iBirths = Integer.parseInt(sBirths);
                    totalBirths += iBirths;
                    }
            }
        }
        return totalBirths;
    }
    public float getAverageRank(String name, String gender) {
        DirectoryResource dr = new DirectoryResource();
            int totalRank = 0;
            int fileCount = 0;
            float aveRank = 0;
            for (File f: dr.selectedFiles()){
                FileResource fr = new FileResource(f);
                String year = f.getName().substring(3,7);
                int intYear = Integer.parseInt(year);
                
                int currentRank = getRank(intYear,name,gender);
                if (currentRank !=-1){
                totalRank += currentRank;
                fileCount += 1;
            }
            
    }
        String sTotalRank = Integer.toString(totalRank);
        float fTotalRank = Float.parseFloat(sTotalRank);
        if (fileCount != 0){
        aveRank = fTotalRank/fileCount;
    }
    else {return -1;}
        
        return aveRank;
        
}
    public int yearOfHighestRank(String name, String gender){
        DirectoryResource dr = new DirectoryResource();
            int highestRank = 9999;
            int highestYear = 0;
        for (File f : dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            String fileYear = f.getName();
            String year = f.getName().substring(3,7);
            int intYear = Integer.parseInt(year);
            int currentRank = getRank(intYear,name,gender);
                        
            if (highestRank == 9999 && currentRank!= -1){
                highestRank = currentRank;
                highestYear = intYear;
            }
               else if (currentRank <highestRank && currentRank != -1){
                    highestRank = currentRank;
                    highestYear = intYear;
                    System.out.println(highestYear);
                }
               
            
            
        }
        if (highestYear == 0) {return -1;}
        else{
        return highestYear;
    }
    }
    public void testGetRank(){
        String nameTest = "Mason";
        String genderTest = "F";
        int rank = getRank(2012, nameTest, genderTest);
        if (rank!=-1){
        System.out.println(nameTest + "\'s rank is " + rank + " among those with gender: " + genderTest); 
    }
    else {System.out.println("No matches for the name: " + nameTest + " found with gender: " + genderTest);
    
    }
}
    public void whatIsNameInYear(String name, int year, int newYear, String gender){
        //String fn = "us_babynames\\us_babynames_test\\yob"+year+"short.csv";
        //FileResource fr = new FileResource(fn);
        //CSVParser parser = fr.getCSVParser();
        int rank = getRank(year, name, gender);
        String newName = getName(newYear, rank, gender);
        System.out.println(name + " born in " + year + " would be named " 
                                    + newName + " if born in " + newYear);
    }
    public int getRank(int year, String name, String gender){
        //set maleIndex as total # girls, since girls all come before guys
        int rankCount = 0;
        String fn = "us_babynames\\us_babynames_by_year\\yob"+year+".csv";
        FileResource fr = new FileResource(fn);
        CSVParser parser = fr.getCSVParser(false);
        for (CSVRecord record : parser){
             if(record.get(1).equals(gender)){
                rankCount +=1;
                if (record.get(0).equals(name)){
                    return  rankCount;
                }
            }
    } 
    rankCount = -1;
    return rankCount;
        }
    public  String getName(int year, int rank, String gender){
        int maleIndex = 0;
        String fn = "us_babynames\\us_babynames_by_year\\yob" + year + ".csv";
        FileResource fr = new FileResource(fn);
        CSVParser parser = fr.getCSVParser(false);
        for (CSVRecord record : parser){
            if (record.get(1).equals("F")){
                maleIndex +=1;
            }
        }
        parser = fr.getCSVParser(false);
        for(CSVRecord record: parser){
            long recordNumber = record.getRecordNumber();
            int intRecNum = (int)recordNumber;
            if (gender.equals("F")){
            if (rank == intRecNum && record.get(1).equals("F")){
                return record.get(0);
            }
        }
        if (gender.equals("M")){
            if ((rank + maleIndex) == intRecNum &&record.get(1).equals("M")){
                return record.get(0);
            }
        }
            
        }
        return "No name";
    } 
        
    }
   
    

