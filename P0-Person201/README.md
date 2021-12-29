# Project 0: Person201

## Outline
- [Goals](#goals)
- [Installing IntelliJ, Git](#installing-intellij-git)
- [Starter Code and Using Git](#starter-code-and-using-git)
- [Developing the classes in Project P0: Person201](#developing-the-classes-in-project-p0-person201)
- [Run **Person201Driver.java** and change **Person201.java**](#run-person201driverjava-and-change-person201java)
- [Running **Person201Scanner** and changing Data Source](#running-person201scanner-and-changing-data-source)
- [Running **Person201Scanner** from a Web Source](#running-person201scanner-from-a-web-source)
- [Create and Run a New Java Class **Person201Solo.java**](#create-and-run-a-new-java-class-person201solojava)
- [Post and Get using a RESTful service](#post-and-get-using-a-restfull-service)
- [Checklist](#checklist)
- [Submission](#submission)
- [Reflect](#reflect)
- [Grading](#grading)


# Project 0: Person201


## Goals
<details>
<summary>
Click to Expand Goals of the PRoject
</summary>

* To create a simple Java class and modify two driver programs to understanding of classes, methods, and instance variables.
* To learn about running Java programs using the IntelliJ IDE
* To learn about using Git for project management.
* To create a text file that is read by a Java program.
* To run programs that make post and get requests to a REST-ful webservice.
* To learn about CompSci201 workflow for assignments: Git, Gradescope, helper hours.

</details>


## Installing IntelliJ, Git
<details>
<summary>
Click to Expand Installing Software
</summary>

To do this project, you must have IntelliJ and Git setup on your device. See the [directions][Installing Software for CompSci201] for help getting set up. 

**_You must have installed all software (Java, Git, IntelliJ) mentioned on this guide before proceeding with the rest of the document._**

</details>

## Starter Code and Using Git
<details>
<summary>
Click to Expand Getting Starter Code with Git
</summary>

We'll be using Git and the installation of GitLab at https://coursework.cs.duke.edu. All code for classwork, discussion, and assignments will be accessible via this site.

**_Now, you should fork and clone the P0 repo using the instructions in Part 1 of [this document](https://docs.google.com/document/d/1dlEwDwiIyEQFxXOHS_zY-Qojx4djl4p2Ud16qpeb7gY/edit?usp=sharing)._**

1. What this means is that you should login to https://coursework.cs.duke.edu. 
2. Visit the repository for this project: https://coursework.cs.duke.edu/201fall21/P0-Person201/ and fork it.
3. Clone the repository using a terminal and the command line as described in detail (alternatively you can use IntelliJ).

**_We strongly recommend that after finishing coding or about every hour, you push your changes to the repo as explained in Part 2 of [this document][Using IntelliJ, Gradescope, and Git]._**
</details>

## Developing the classes in Project P0: Person201
<details>
<summary>
Click to Expand Developing Classes in Project
</summary>

When you fork and clone the project, you'll be working primarily within the src folder with .java files beginning with `Person201`. Your goal is to modify three programs/classes (`Person201.java`, `Person201Driver.java`, and `Person201Scanner.java`) and create a new program (`Person201Solo.java`) to generate the desired output. You'll also run two programs (`PostPerson.java`, `PeopleDownloader.java`) as described below in answering the questions in the analysis document you submit with this and every 201 project. For this project, the analysis will be done in the [P0 Reflect][P0 Reflect] document for the project.


### Run `Person201Driver.java` and change `Person201.java`
<details>
<summary>Initial Runs and Changes</summary>

First run the main method in `Person201Driver.` (Refer to [this document][Using IntelliJ, Gradescope, and Git] for how to run a Java class). The output of the program will be:

```
no-name woto @ 35.9312N 79.0058W
Ricardo harambee @ 34.6037S 58.3816W
Gelareh affective @ 33.89S 151.2E
name woto
name woto
```

Open `Person201.java` in the IntelliJ editor, and look at each of the three `//TODO: change here` comments in the `Person201.java` class. Fix the code so that when the same `Person201Driver` main program is run the output is as shown below:

```
Owen woto @ 35.9312N 79.0058W
Ricardo harambee @ 34.6037S 58.3816W
Gelareh affective @ 33.89S 151.2E
Ricardo harambe
Gelareh affective
```

You can accomplish the above in three steps:
1. Changing the value assigned to instance variable `myName` in the default constructor
2. Changing the body of the method `getPhrase` to return the person's phrase (use an instance variable).
3. Changing the body of the method `getName` to return the person's name (use an instance variable).

Now that you've done this, change the `main` method in class `Person201Driver` by creating a new `Person201` variable named s (short for Sam) with the value shown:

`Person201 s = new Person201("Sam", 44.9978, -93.2650, "hello");`

Add one `System.out.println` statement to print the value of this variable `s` so the output of running the program is as follows:

```
Owen woto @ 35.9312N 79.0058W
Ricardo harambee @ 34.6037S 58.3816W
Gelareh affective @ 33.89S 151.2E
Sam hello @ 44.9978N 93.265W
Ricardo harambee
Gelareh affective
```
</details>

### Running Person201Scanner and changing Data Source
<details>
<summary>Scanning from Another File</summary>

Once the `Person201` class has been updated so that `Person201Driver` generates output as shown above, you should run `Person201Scanner` to see the output below -- running the program is described after the output.

```
Owen woto @ 35.9312N 79.0058W
Ricardo harambee @ 34.6037S 58.3816W
Gelareh affective @ 33.89S 151.2E
total # 3
```

You'll then need to Run `Person201Scanner`. You can do this by either 
1. Right clicking on the file 
as seen here: ![Selecting the file to run in IntelliJ](./p0-figures/P0-SelectFile.png) and then moving your cursor to the Run option. ![Running selected file in IntelliJ](./p0-figures/P0-RunningSelectedFile.png)
2. Go to the Run menu in the top toolbar ![Run menu in IntelliJ](./p0-figures/P0-RunMenu.png) and use the small sub-menus to specify `Person201Scanner`. Then you'll be able to toggle between `Person201Driver` and `Person201Scanner` using the Run menu or the shortcut above the edit panes with the small green arrow that's shown below.

You should edit the `main` method of `Person201Scanner.java` so that the file `data/large.txt` is used as the source of data. This data file includes a random set of names, locations, and words from several sources. You should see 97 different names, phrases and latitude/longitude locations.
</details>

### Running Person201Scanner from a Web Source
<details>
<summary>Scanning from a Web source</summary>

In the `main` method of `Person201Scanner` create a new `String` variable after the first line, as shown below (you can copy/paste):
```bash
String url = "https://courses.cs.duke.edu/compsci201/current/data/medium.txt";
```
Then change the assignment to variable list so that it is:
```bash
Person201[] list = readURL(url);
```
Run the program and note the last name and the number of names printed to answer the questions in the [P0 reflect document][P0 Reflect].
</details>

### Create and Run a New Java Class: **Person201Solo.java**
<details>
<summary>Creating and running a new class</summary>


In the `src` folder create a new Java class named `Person201Solo` that has only a `public static void main method` that allows the program to run (the `main` method is the launch point for all Java programs when they are executed). See `Person201Driver` for details and an example of a `main` method. In the new `main` method, you should define a `Person201` object as shown below and print using `System.out.println(person)` so that the main method has two statements.

```
Person201 person = new Person201("Sam", -77.846, 166.668, "cold");
System.out.println(person);
```
</details>
</details>

### Post and Get using a RESTfull service:
<details>
<summary>Connecting to a RESTful web service</summary>

Run the program `PeopleDownloader.java` and you'll see information for many Person201 objects printed, these are obtained from a RESTful web service, details don't matter for this program, but the number of people printed will change as more students in 201 run the next program in this section, `PostPerson.java`. You should run `PeopleDownloader.java` and then make a change by adding one line to print the number of lines printed, which is the value of `pa.length` -- you can add a print statement after the `for` loop. Make a note of this number in the [P0 reflect][P0 reflect] form.

Then modify the program `PostPerson.java`  by changing line 48 in the `main` method so that the `Person201 p` variable represents information about you. Include your name, a phrase you'd like associated with yourself, and the latitude and longitude of the town you consider either where you grew up, or where you went to high school before Duke. You'll need to look up the these latitude and longitude values using a search engine. After you modify the program, **run** it so that the informatoon you created is posted ot the webservice for the class. You can verify that this ran succesfully by runing `PeopleDownloader.java` again and seeing your information printed.

You're then ready to submit the project for grading. You'll also need to answer the questions in the [P0 reflect document][P0 Reflect].
</details>

</details>

## Checklist
<details>
<summary>
Click to Expand Checklist for submission
</summary>

Before you submit to Gradescope, check that you've done each of the following:

- Modify `Person201.java` by changing code in three places.
- Verify that running `Person201Driver.java` matches the expected output after modifying `Person201.java`
- Add a new `Person201` object in the `Person201Driver.java` program and verify that running `Person201Driver.java` matches the expected output after modifying `Person201Driver.java`.
- Run `Person201Scanner.java` with the data file `data/large.txt`.
- Run `Person201Scanner.java` with a web source via a specified URL.
- Create a new class `Person201Solo` with a `main` method that has two statements.
- Run the program `PostPerson` to post new information and `PeopleDownloader` to get the information from a webservice.
</details>

## Submission
<details>
<summary>
Click to Expand How to Submit
</summary>

You will submit the assignment on Gradescope. [Here][Using IntelliJ, Gradescope, and Git] is a document that describes the submission process in detail. 

You can login to https://www.gradescope.com (make sure to login with “School Credentials”), find project P0 and submit your code. **You CANNOT submit unless all your code has been pushed to your Git repository on coursework.** So, be sure to push changes often and be sure your final program is in your Git repository before you get it graded on Gradescope.

You will need to resubmit your entire project on Gradescope every time after you make changes that you wish to be graded. Please take note that changes/commits on GitLab are NOT automatically synced to Gradescope.
</details>


## Reflect

After completing the coding portion, fill out the reflect form here: [P0 Reflect][P0 Reflect]

## Grading
<details>
<summary>Click for Grading Summary</summary>

Your submission will be graded by the following rubric:

| Class Modified | Points |
| ------ | ------ |
| Person201 | 4 |
| Person201Driver | 4 |
| Person201Solo | 4 |
| Webservice | 4 |
| Reflect form | 4 |

</summary>

<!-- ALL LINKS USED IN THIS DOCUMENT -->

[Installing Software for CompSci201]: https://docs.google.com/document/d/1dlEwDwiIyEQFxXOHS_zY-Qojx4djl4p2Ud16qpeb7gY/edit?usp=sharing

[Using IntelliJ, Gradescope, and Git]:https://docs.google.com/document/d/1dlEwDwiIyEQFxXOHS_zY-Qojx4djl4p2Ud16qpeb7gY/edit?usp=sharing

[P0 Reflect]:https://docs.google.com/forms/d/e/1FAIpQLSdfMS68L3plwZuLtXlr1Jbwudv6L15zhx9QShlh3yoabMH77w/viewform
