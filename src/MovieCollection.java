import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.util.Collections.reverseOrder;
import static java.util.Collections.sort;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;
    ArrayList<String> cast = new ArrayList<>();
    ArrayList<String> genres = new ArrayList<>();
    ArrayList<String> top50Rated = new ArrayList<>();
    ArrayList<String> top50Revenue = new ArrayList<>();

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
        for (int i = 0; i < movies.size(); i++) {
            String[] castArray = movies.get(i).getCast().split("\\|");

            for (String casts : castArray) {
                String trimmedCast = casts.trim().toLowerCase();
                if (!cast.contains(trimmedCast))
                {
                    cast.add(trimmedCast);
                }
            }
        }
        for (int i = 0; i < movies.size(); i++) {
            String[] genreArray = movies.get(i).getGenres().split("\\|");
            for (String genre : genreArray) {
                String trimmedGenre = genre.trim().toLowerCase();
                if (!genres.contains(trimmedGenre))
                {
                    genres.add(trimmedGenre);
                }
            }
        }
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a tital search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        System.out.println("What cast member would you like to see?");
        String searchTerm = scanner.nextLine();
        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<String> results = new ArrayList<String>();

        // search through ALL movies in collection and add the cast member into the results
        for (String castMember : cast)
        {
            if (castMember.contains(searchTerm))
            {
                results.add(castMember);
            }
        }
        System.out.println(results);
        // sort the results by title
        sort(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String castMember = results.get(i);

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + castMember);
        }

        System.out.println("Which actor would you like to learn more about?");
        System.out.print("Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        String actor = results.get(choice-1);
        ArrayList<Movie> movies1 = new ArrayList<Movie>();
        for (Movie movie : movies)
        {
            if (movie.getCast().toLowerCase().contains(actor.toLowerCase()))
            {
                movies1.add(movie);
            }
        }
        for (int i = 0; i < movies1.size(); i++)
        {
            String title = movies1.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice1 = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = movies1.get(choice1 - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }


    private void searchKeywords() {
        System.out.print("Enter a keyword search term: ");
        String searchTerm = scanner.nextLine();


        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();


        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();


        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++) {
            String keywords = movies.get(i).getKeywords();
            keywords = keywords.toLowerCase();


            if (keywords.indexOf(searchTerm) != -1) {
                //add the Movie object to the results list
                results.add(movies.get(i));
            }
        }
    }



    private void listGenres()
    {
        System.out.println("What genre would you like to see?");
        String searchTerm = scanner.nextLine();
        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<String> results = new ArrayList<String>();

        // search through ALL movies in collection and add the cast member into the results
        for (String genre : genres)
        {
            if (genre.contains(searchTerm))
            {
                results.add(genre);
            }
        }
        System.out.println(results);
        // sort the results by title
        sort(results);
        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String castMember = results.get(i);

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + castMember);
        }

        System.out.println("Which genre would you like to learn more about?");
        System.out.print("Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        String genre = results.get(choice-1);
        ArrayList<Movie> movies1 = new ArrayList<Movie>();
        for (Movie movie : movies)
        {
            if (movie.getGenres().toLowerCase().contains(genre.toLowerCase()))
            {
                movies1.add(movie);
            }
        }
        for (int i = 0; i < movies1.size(); i++)
        {
            String title = movies1.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice1 = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = movies1.get(choice1 - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRated()
    {
        ArrayList<Double> temp = new ArrayList<>();
        ArrayList<Double> ratings = new ArrayList<>();
        ArrayList<Movie> top50Movies = new ArrayList<>();
        for (Movie movie: movies)
        {
            temp.add(movie.getUserRating());
        }
        sort(temp);
        for (int i = temp.size()-1; i >= temp.size()-50; i--) {
            ratings.add(temp.get(i));
        }
        for (int i = 0; i < ratings.size(); i++) {
            for (int j = i+1; j < ratings.size();) {
                if (ratings.get(i).equals(ratings.get(j))) {
                    ratings.remove(j);
                } else {
                    j++;
                }
            }
        }
        int count = 0;
        while (top50Movies.size() <50) {
            for (Movie movie : movies) {
                if ((movie.getUserRating() == ratings.get(count)) && top50Movies.size()!= 50) {
                    top50Movies.add(movie);
                }
            }
            count++;
        }
        for (int i = 0; i < top50Movies.size(); i++)
        {
            String title = top50Movies.get(i).getTitle();
            double rating = top50Movies.get(i).getUserRating();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title + ": " + rating);
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice1 = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = top50Movies.get(choice1 - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRevenue()
    {
        ArrayList<Integer> temp = new ArrayList<>();
        ArrayList<Integer> revenue = new ArrayList<>();
        ArrayList<Movie> top50Revenue = new ArrayList<>();
        for (Movie movie: movies)
        {
            temp.add(movie.getRevenue());
        }
        sort(temp);
        for (int i = temp.size()-1; i >= temp.size()-50; i--) {
            revenue.add(temp.get(i));
        }
        int count = 0;
        while (top50Revenue.size() <50) {
            for (Movie movie : movies) {
                if ((movie.getRevenue() == revenue.get(count)) && top50Revenue.size()!= 50) {
                    top50Revenue.add(movie);
                }
            }
            count++;
        }
        for (int i = 0; i < top50Revenue.size(); i++)
        {
            String title = top50Revenue.get(i).getTitle();
            double rating = top50Revenue.get(i).getRevenue();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title + ": " + rating);
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice1 = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = top50Revenue.get(choice1 - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}