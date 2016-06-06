# jkcd
An xkcd viewer written in Java.

![jkcd program in action](http://imgur.com/4FRZ97vl.png)

## Inspiration
An rewrite of my previous program in Bash, [xkcd-client](https://www.github.com/jlam55555/jkcd).

## Instructions
Here are two methods to run the program.

Both steps require that you have either the JDK8 or JVM8 (Java 8).

1. **Using the pre-built executable-jar file**:
    a. Navigate to the `dist/jkcd` folder using a file explorer and double-click the `jkcd.jar` file.
    b. Navigate to the `dist/jkcd` folder using the terminal and run: `java -jar jkcd.jar`.
2. **Compiling the program yourself (requires JDK8)** (use if you want more control or if step 1 does not work):
    a. Navigate to the `bin` folder, and run the following commands: `javac ../src/jkcd.java -d ./ -cp .unbescape.jar:.;jar cfm ../dist/jkcd/jkcd.jar manifest.txt *.class style.css`. Then, follow step 1 to run the executable-jar file.

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
