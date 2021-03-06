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
            line= br.readLine();
            while ((line=br.readLine())!=null){
                int index = line.lastIndexOf("`");
                String[] temp={line.substring(0,index),line.substring(index+1)};
                data.put(temp[0],temp[1]);
            }
            br.close();
        }catch (Exception ex){
        }
    }

    public Database(Database database) {
        this.data= (HashMap<String, String>) database.data.clone();
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
        List<String> l= new ArrayList<>(data.values());
        Collections.sort(l);
        return l;
    }

    public String findTitle(String title){
        String result=null;

        for(Map.Entry<String,String> entry: data.entrySet()){
            if(entry.getValue().contains(title)){
                result=entry.getKey();
                break;
            }
        }

        return result;
    }

    public boolean addNew(String title, String meaning){
        if(findMeaning(title)==null)
        {
            data.put(title,meaning);
            return true;
        }
        return false;
    }

    public boolean editSlang(String title, String meaning){
        try{
            data.put(title,meaning);
            return true;
        }catch (Exception ex){
            return false;
        }
    }
    public boolean delete(String title){
        data.remove(title);
        return true;
    }

    public void save(){
        try{
            BufferedWriter br = new BufferedWriter(new FileWriter("slang.txt"));
            br.write("Slang`Meaning");
            br.newLine();
            for(Map.Entry<String, String> entry : data.entrySet()) {
                br.write(entry.getKey()+"`"+entry.getValue());
                br.newLine();
            }
            br.flush();
            br.close();
        }catch (Exception ex){
        }
    }


}
