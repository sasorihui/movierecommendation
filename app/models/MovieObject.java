package models;
/**
 * This class creates a MovieObject which stores the user ID and the dotproduct
 * 
 * @author Daniel Obaseyi Buraimo
 * 
 */
public class MovieObject implements Comparable<MovieObject> {
	/**
	 * This is the id of the user
	 */
	private final String userid;
	/**
	 * This is the dot product
	 */
	private double correlation;

	/**
	 * This constructor initializes the MovieObject
	 * 
	 */
	public MovieObject(String id,double correlation) {
		this.userid = id;
		this.correlation = correlation;
	}

	/**
	 */
	public double getProduct() {
		return this.correlation;
	}
	
	/**
	 */
	public String getID() {
		return this.userid;
	}

	/**
	 * Changes the initial position of a particular SearchResultObject
	 * 
	 * @param newPosition
	 *            the new initial position
	 */

	public void setProduct(double correlation) {
		this.correlation = correlation;
	}


	/**
	 * This method overrides the Object's toString method. It creates a format
	 * in which this SearchResultObject can be easily read. Either into the
	 * console or a file.
	 * 
	 * @return the format of the output of this SearchResultObject
	 */
	@Override
	public String toString() {

		return "User: " + userid + " has a pearson correlation of  " + correlation;
	}



	public int compareTo(MovieObject arg0) {
		if (this.correlation > arg0.correlation)
			return -1;
		else if (this.correlation == arg0.correlation)
			return lastCompareTo(arg0);
		else
			return 1;
	}

	/**
	 * This method is used to compare different SerachResultObjects by their
	 * filename
	 * 
	 * @param arg0
	 *            the other SearchResultObject being compared with
	 * 
	 * @return the value of the comparisons
	 */
	private int lastCompareTo(MovieObject arg0) {
		if (this.userid.compareToIgnoreCase(arg0.userid) < 0)
			return -1;
		if (this.userid.compareToIgnoreCase(arg0.userid) > 0)
			return 1;
		else
			return 0;
	}


}