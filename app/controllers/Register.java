package controllers;

import play.data.*;
import play.mvc.*;
import models.User;
import models.AllUsers;
import views.html.*;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.util.Map.Entry;


//Added by Daniel
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;


public class Register extends Controller {

	final static Form<User> userForm = Form.form(User.class);
	final static AllUsers allusers = new AllUsers();
    static ArrayList<Integer> movieIds = null;

    public static Result index() throws IOException {
        File file = new File("conf/movies.txt");
        File userfile = new File("conf/users.txt");
        allusers.movieParse(file);
        allusers.userParse(userfile);
        movieIds = allusers.getTenRandomIDS();
        return ok(regpage.render(userForm, allusers.shortlist));
    }
    
    
    public static Result submit() throws IOException {
        Form<User> filledForm = userForm.bindFromRequest();
        User created = filledForm.get();
        created.addToRatings(movieIds);
        allusers.addToAll(created.username, created);
        return ok(submit.render(created));
        
    }
    
 /*   public static Result load() {
        Form<User> filledForm = userForm.bindFromRequest();
        User created = filledForm.get();
        created.addToRatings(movieIds);
        allusers.addToAll(created.username, created);
    	movieIds.clear();
    	movieIds = allusers.getTenRandomIDS();
        return ok(loadmore.render(userForm, allusers.shortlist));

    }*/
    
    public static Result recommend() throws IOException {
        movieIds.clear();
        User user = allusers.getUserData("2");
        allusers.checkForSimUsers("2", user.simmovies);
        ArrayList<String> recMovies = new ArrayList<String>();
        for(int i = 0; i < user.simmovies.size(); i++)
        {
            String movie = allusers.findMovie(user.simmovies.get(i));
            movieIds.add(user.simmovies.get(i));
            recMovies.add(movie);
        }
        return ok(somethingelse.render(userForm, recMovies));
    }
    
    public static Result submitrec() throws IOException {
        //Added by Daniel
        PrintWriter outw = new PrintWriter(new BufferedWriter(new FileWriter("data/dataset.csv", true)));

        Form<User> filledForm = userForm.bindFromRequest();
    	User newdata = filledForm.get();
    	newdata.addToRatings(movieIds);
    	User olddata = allusers.getUserData("2");

    	for(int i = 0; i < newdata.ratingssize; i++)
    	{
    	    //Added by Daniel
    	    System.out.println("2," + newdata.movies.get(i) + "," + newdata.ratings.get(i));
    	    outw.println("2," + newdata.movies.get(i) + "," + newdata.ratings.get(i)+".0");
    	    olddata.userdata.put(newdata.movies.get(i), newdata.ratings.get(i));
    	}
    	//Added by Daniel
        outw.close();
    	allusers.addToAll("2", olddata);
    	olddata.simmovies.clear();
    	movieIds.clear();
    	allusers.checkForSimUsers("2", olddata.simmovies);
        ArrayList<String> recMovies = new ArrayList<String>();
        for(int i = 0; i < olddata.simmovies.size(); i++)
        {
            String movie = allusers.findMovie(olddata.simmovies.get(i));
            movieIds.add(olddata.simmovies.get(i));
            recMovies.add(movie);
        }

    	return ok(somethingelse.render(userForm, recMovies));
    }


}
