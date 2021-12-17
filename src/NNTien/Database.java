package NNTien;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

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
            br.close();
        }catch (Exception ex){
        }
    }
    public List<String> title(){
        List<String> l= new ArrayList<>(data.keySet());
        Collections.sort(l);
        return l;
    }
    public String findMeaning(String title){
        return data.get(title);
    }

    public List<String> meaning(){
        return new ArrayList<>(data.values());
    }

    public String findTitle(String title){
        String result=null;

        for(Map.Entry<String,String> entry: data.entrySet()){
            if(Objects.equals(entry.getValue(),title)){
                result=entry.getKey();
                break;
            }
        }

        return result;
    }
}
