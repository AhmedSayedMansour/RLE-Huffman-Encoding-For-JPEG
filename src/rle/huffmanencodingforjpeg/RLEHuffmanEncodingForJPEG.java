package rle.huffmanencodingforjpeg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RLEHuffmanEncodingForJPEG {
    
    public static HashMap<String,String> huffmanTable = new HashMap<>();     //Huffman Table 
    public static ArrayList<String> nonZeros = new ArrayList<>();           //Non zero nummers
    
    public String Encode(String in){
        String output = "";
        ArrayList<String> Discriptors = IntoDiscriptors(in);    //Discriptors
        Huffman Huff = new Huffman();
        huffmanTable = Huff.getHuffmanTable(Discriptors);
        for(int i = 0 ; i<Discriptors.size()-1;++i){
            output+=( huffmanTable.get(Discriptors.get(i)) + toBinary(nonZeros.get(i)) );
        }
        output += huffmanTable.get(Discriptors.get(Discriptors.size()-1));
        return output;
    }
    
    public String Decode(String s){
        ArrayList<String> Discriptors = new ArrayList<>();
        String out="";
        for(int i=0 ; i<s.length();++i ){
            String cur = "";
            while(!huffmanTable.containsValue(cur)){
                cur+=(s.charAt(i)+"");
                i++;
            }
            String Discriptor = "";
            for(String k : huffmanTable.keySet()){
                if(huffmanTable.get(k).equals(cur)){
                    Discriptor = k;
                    Discriptors.add(k);
                    break;
                }
            }
            if(i == s.length()){
                return out+"EOB";
            }
            String bin = "";
            for(int j=0 ; j< Integer.parseInt(Discriptor.charAt(Discriptor.length()-1) +"");++j ){
                bin+= (s.charAt(i)+"");
                i++;
            }
            i--;
            for(int j=0 ; j< Integer.parseInt(Discriptor.charAt(0) +"");++j ){
                out+= "0,";
            }
            out+=(toDecimal(bin)+",");
        }
        return out;
    }
    
    public static ArrayList<String> IntoDiscriptors(String inp){
        String[] arr = inp.split(",");
        ArrayList<String> Discriptors = new ArrayList<>();
        int zeros = 0;
        for (int i = 0 ; i< arr.length ;++i){
            zeros = 0;
            if(arr[i].equals("EOB")){
                nonZeros.add(arr[i]);
                Discriptors.add("EOB");
            }
            else{
                while (arr[i].equals("0")){
                    zeros++;
                    i++;
                }
                nonZeros.add(arr[i]);
                Discriptors.add(zeros + "/" + toBinary(arr[i]).length());
            }
        }
        return Discriptors;
    }
    
    public static String toBinary(String num){
        if(num.charAt(0) == '-'){
            int decimal = ~Integer.parseInt(toBinary(num.substring(1)),2);
            String out = Integer.toBinaryString(decimal);
            return out.substring(out.indexOf("0", 0));
        }
        else{
            return Integer.toBinaryString(Integer.parseInt(num));
        }
    }
    
    public static String toDecimal(String bin){
        if(bin.charAt(0) == '0'){
            String out ="";
            for (int i = 0 ; i< bin.length() ;++i){
                if(bin.charAt(i) == '0')    out+="1";
                else    out+="0";
            }
            return "-"+Integer.parseInt(out, 2);
        }
        else{
            return Integer.parseInt(bin,2)+"";
        }
    }
    
    public void SaveToFiles(String code,HashMap<String,String> arr) throws IOException{
        FileWriter myWriter = new FileWriter("Compression.txt");
        myWriter.write(code);
        myWriter.close();
        FileWriter myWriter2 = new FileWriter("HuffmanTable.txt");
        String line = "";
        for(String i : arr.keySet()){
            myWriter2.write(line +i +" "+ arr.get(i));
            line = "\n";
        }
        myWriter2.close();
    }
    
    public HashMap<String,String>  getHuffmantable(){
        return huffmanTable; 
    }
    
    public String ReadFromFiles() throws FileNotFoundException{
        File myObj = new File("Compression.txt");
        Scanner myReader = new Scanner(myObj);
        String data = myReader.nextLine();
        return data;
    }
}
