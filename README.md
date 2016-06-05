# jkcd
An xkcd viewer written in Java.

An rewrite of my previous program in Bash, [xkcd-client](https://www.github.com/jlam55555/jkcd).

## Instructions
Because the `*.class` files are compiled using my computer, the first method (easier) may or may not work. If it doesn't, default to the second one.
1. *If you have an x86 architecture processor and Java 8:* To run the program, navigate to the `bin` directory and run the following: `java jkcd`.
2. *For all other cases:* Navigate to the `src` directory and run the following to compile to the `bin` directory and run it:
  ```
  javac jkcd.java
  cd ../bin
  java jkcd
  ```

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
