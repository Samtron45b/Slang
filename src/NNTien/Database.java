package NNTien;

import java.io.*;
import java.util.*;
/**
 * NNTien
 * Created by user
 * Date 12/15/2021 - 3:18 PM
 * Description: ...
 */
public class Database {
    private HashMap<String,String> data = new HashMap<>();

    public Database(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("slang.txt"));
            String line;
            while ((line=br.readLine())!=null){
                int index = line.lastIndexOf("`");
                String[] temp={line.substring(0,index),line.substring(index+1)};
                data.put(temp[0],temp[1]);
            }
        }catch (Exception ex){
        }
    }
    public List<String> title(){
        List<String> l= new ArrayList<>(data.keySet());
        Collections.sort(l);
        return l;
    }

    public List<String> meaning(){
        return new ArrayList<>(data.values());
    }
}
