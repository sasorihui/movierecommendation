package models;
import java.util.ArrayList;
import java.util.TreeMap;
import models.User;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.*;
import java.util.Map.Entry;



public class AllUsers {
    
    /*TreeMap<Username, Userdata>*/
    TreeMap<String, User> allusers = new TreeMap<String, User>();
    /*ArrayList<MovieTitles> index would be movieID*/
    ArrayList<String> allmovies = new ArrayList<String>();
    /*ArrayList<genres> index would be movieID*/
    ArrayList<String> allgenres = new ArrayList<String>();
    
    public ArrayList<String> shortlist = new ArrayList<String>();
    
    public int getSizeOfAll(){
        return allusers.size();
    }
    
    
    public void movieParse(File moviefile) throws IOException{
		String line;
		BufferedReader input = null;

		try {
			input = new BufferedReader(new FileReader(moviefile));

			while((line = input.readLine()) != null) {
				String[] wordArray = line.split("[|]+");
				allmovies.add(wordArray[1]);
			}
			
		} catch(FileNotFoundException e) {
			System.out.println("File Not Found");

		} catch (IOException e) {
			System.out.println("File is not readable");
			
		} finally {
			input.close();
			
		}
	}
	
	public void userParse(File userfile) throws IOException{
	    String line;
		BufferedReader input = null;

		try {
			input = new BufferedReader(new FileReader(userfile));

			while((line = input.readLine()) != null) {
				String[] wordArray = line.split("[\t]+");
				String user = wordArray[0];
				allusers.put(user, new User());
				
				for(int i = 1; i < wordArray.length; i++){
					
						String[] rating = wordArray[i].split("[,]");
						allusers.get(user).userdata.put(Integer.parseInt(rating[0]), Integer.parseInt(rating[1]));
					}

			}

			
		} catch(FileNotFoundException e) {
			System.out.println("File Not Found");

		} catch (IOException e) {
			System.out.println("File is not readable");
			
		} finally {
			input.close();
			
		}
	}
    
    public void addToAll(String username, User user){
        
        
        allusers.put(username, user);  
        System.out.println(user.userdata.toString());
        
        
    }
    
    public User getUserData(String username){
        return allusers.get(username);
    }
    
    public String findGenre(int id){
        return allgenres.get(id);
    }
    
    public String findMovie(int id){
        return allmovies.get(id);
    }
    
    public ArrayList<Integer> getTenRandomIDS(){
        ArrayList<Integer> random = new ArrayList<Integer>();
        for(int i = 0; i < 10; i++)
        {
          random.add((int)(Math.random() * allmovies.size()) + 1);  
        }
        makeRandomList(random);
        return random;
    }
    
    public void makeRandomList(ArrayList<Integer> movieIds){
        for(int i = 0; i < movieIds.size(); i++){
            shortlist.add(findMovie(movieIds.get(i)));
        }
    }
    
    
    public void checkForSimUsers(String user, ArrayList<Integer> movies){
		
		TreeMap<Integer, Integer> userMap = allusers.get(user).userdata;
	
	    ArrayList<String> userList = new ArrayList<String>();
	    
	    while (userList.size() != 10) {
						
			compareUsers(user, userMap, userList);
		}
		
		
		
		for(int i = 0; i < userList.size(); i++){
		    User temp = allusers.get(userList.get(i));
		    for(Entry<Integer, Integer> values : temp.userdata.entrySet()){
		        if(!userMap.containsKey(values.getKey()) && !movies.contains(values.getKey()) && movies.size() < 10){
		            movies.add(values.getKey());
		            break;
		        }
		    }
		    
		}
		
		
					
	}
	

	private void compareUsers (String user, TreeMap<Integer, Integer> userMap, 
			 ArrayList<String> userList){
		
		String line;
		Integer highestDot = 0;
		String simUser = null;
					
					
		for(Entry<String, User>  entry : allusers.entrySet()) {
		       if(!entry.getKey().equals(user) && checkList(userList, entry.getKey())) {
		           Integer dot = 0;
		           for(Entry<Integer, Integer> values : entry.getValue().userdata.entrySet()){
		               if(userMap.containsKey(values.getKey())) {
		                   dot+= userMap.get(values.getKey()) * values.getValue();
		               }
		           }
		           
		           if(dot > highestDot) {
						highestDot = dot;
						simUser = entry.getKey();
					}
		           
		       } 
		        
		}
	    userList.add(simUser);
		
	}
	
	private boolean checkList (ArrayList<String> userList, String user ) {
		
		for(int i = 0; i < userList.size(); i++) {
			
			if(userList.get(i).equals(user)) {
				
				return false;
			}
		}
		
		return true;
	}
    
    
}