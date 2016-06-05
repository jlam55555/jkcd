# jkcd
An xkcd viewer written in Java.

![jkcd program in action](http://imgur.com/bzs3KrYl.png)

## Inspiration
An rewrite of my previous program in Bash, [xkcd-client](https://www.github.com/jlam55555/jkcd).

## Instructions
##### Terminal-based Java
Because the `*.class` files are compiled using my computer, the first method (easier) may or may not work. If it doesn't, then you need the [JDK8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) to compile and run it.

1. *If you have an x86 architecture processor and Java 8:* To run the program, navigate to the `bin` directory and run the following: `java jkcd`.
2. *Otherwise, assuming you have the JDK installed:* Navigate to the `src` directory and run the following to compile: `javac jkcd.java`. This will create the `.class` files in the `bin` directory. Follow step 1 to run the class file.

##### JAR

1. To run the executable JAR, navigate to the `dist` directory and run the following command: `java -jar dist/jkcd.jar`, or double-click on it in a file explorer.
2. If this does not work, you may have to compile it yourself in order to get step 1 to work. Again, ensure you have the JDK8 installed, navigate to the `bin` directory, and then run the following: `jar cfe ../dist/jkcd.jar jkcd *`. This should create the JAR file in the `dist` directory. Follow step one.


## Controls

<table>
  <thead>
    <tr>
      <th>Button Value</th>
      <th>Action</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>&lt;&lt;</td>
      <td>Previous comic** (goto comic with ID = currentId - 1)</td>
    </tr>
    <tr>
      <td>|&lt;</td>
      <td>First comic (goto comic with ID = 1)</td>
    </tr>
    <tr>
      <td>&lt;-</td>
      <td>Back* (goto last comic in history)</td>
    </tr>
    <tr>
      <td>&gt;&gt;</td>
      <td>Next comic** (goto comic with ID = currentId + 1)</td>
    </tr>
    <tr>
      <td>&gt;|</td>
      <td>Last comic (goto comic with ID = lastId)</td>
    </tr>
    <tr>
      <td>-&gt;</td>
      <td>Forward* (goto next comic in history)</td>
    </tr>
    <tr>
      <td>:?</td>
      <td>Get random comic (goto comic from <code>c.xkcd.com/random/comic</code>)</td>
    </tr>
    <tr>
      <td>&lt;&gt;</td>
      <td>Toggle sizing (regular or fit size)</td>
    </tr>
    <tr>
      <td>:#</td>
      <td>Get comic (goto comic with given ID from textfield)</td>
    </tr>
  </tbody>
</table>

\* If there is one

\*\* If reached the end, then loops around
