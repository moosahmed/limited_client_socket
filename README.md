# Table of Contents
1. [Solution Approach](README.md#solution-approach)
2. [Tests](README.md#tests)
3. [Repo directory structure](README.md#repo-directory-structure)
4. [Challenge Description](README.md#challenge-description)

# Solution Approach
Java 1.8 solution to the Data Service Coding Challenge described below.

The Main script is contained in `./src/main/java/com/newrelic/codingchallenge/FiveClientServer.java`.
The entire solution uses `java.io.*` `java.net.*` `java.util.*` No Dependencies. `build.gradle` is also available.

The Server was limited to a Fixed Thread pool, by using the Executors package. The Server does not allow any more than 5
clients to connect at a time.
The Server has a Client Handler class that handles and reads the input streams for each of the clients. responding to
inputs dynamically as they come in.

A Binary Search Tree `BST.java` is implemented to identify duplicates at higher throughput. The Tree organizes the
inputs and also keeps tabs on the size of the Tree and the number of duplicates.

Initially, the Client input streams were directly fed into the Tree. However the process was quite slow. After which,
a blocking queue was implemented, inorder to be more thread safe, and to avoid interleaving of threads. The blocking
queue's capacity is set to 2M, however it is adjustable under `main`. THe blocking que is fed in by WriterThread.java

Unfortunately processing speed wasn't much approved with this method. Test results below. The numbers.log file is saved
in the `out` folder

## Future Directions
Maybe having a concurrent and parallel structure having multiple inner threads that simultaneously feeds into the data
structure, would work much better.

# Tests
A "dummy" client is provided in the test folder. Along with some tests (no assertions).

    Max 5 Clients                           : Successful
    Handling leading zeros                  : Successful
    numbers.log created anew                : Successful
    No duplicates in log file               : Successful
    Non-conforming Data/Client disconnect   : Successful
    Summary standard output                 : Successful
    terminate / system shutdown             : Successful

2M numbers across 5 clients test, Specs:
* 14 seconds for 2M numbers to get in que.
* Unfortunately only ~250 numbers/second get written to numbers.log

## Repo directory structure

    .
    ├── README.md
    ├── build.gradle
    ├── gradlew
    ├── src
    │   └── main/java/com/newrelic/codingchallenge
    │       └── BST.java
    │       └── FiveClientServer.java
    │       └── Queuer.java
    │   └── test/java/com/newrelic/codingchallenge
    │       └── FiveSocketTest.java
    │       └── Client.java
    ├── gradle/wrapper
    │   └── ...

# References
1. http://www.baeldung.com/a-guide-to-java-sockets
2. https://algs4.cs.princeton.edu/32bst/BST.java.html

# Challenge Description
Write a server (“Application”) in Java that opens a socket and restricts input to at most 5 concurrent clients. Clients
will connect to the Application and write any number of 9 digit numbers, and then close the connection. The Application
must write a de-duplicated list of these numbers to a log file in no particular order.

### Primary Considerations
The Application should work correctly as defined below in Requirements.
The overall structure of the Application should be simple.
The code of the Application should be descriptive and easy to read, and the build method and runtime parameters must be
well-described and work.
The design should be resilient with regard to data loss.
The Application should be optimized for maximum throughput, weighed along with the other Primary Considerations and the
Requirements below.

### Requirements
The Application must accept input from at most 5 concurrent clients on TCP/IP port 4000.
Input lines presented to the Application via its socket must either be composed of exactly nine decimal digits
(e.g.: 314159265 or 007007009) immediately followed by a server-native newline sequence; or a termination sequence as
detailed in #9, below.
Numbers presented to the Application must include leading zeros as necessary to ensure they are each 9 decimal digits.
The log file, to be named "numbers.log”, must be created anew and/or cleared when the Application starts.
Only numbers may be written to the log file. Each number must be followed by a server-native newline sequence.
No duplicate numbers may be written to the log file.
Any data that does not conform to a valid line of input should be discarded and the client connection terminated
immediately and without comment.
Every 10 seconds, the Application must print a report to standard output:
The difference since the last report of the count of new unique numbers that have been received.
The difference since the last report of the count of new duplicate numbers that have been received.
The total number of unique numbers received for this run of the Application.
Example text for #8: Received 50 unique numbers, 2 duplicates. Unique total: 567231
If any connected client writes a single line with only the word "terminate" followed by a server-native newline sequence
, the Application must disconnect all clients and perform a clean shutdown as quickly as possible.
Clearly state all of the assumptions you made in completing the Application.

### Notes
You may write tests at your own discretion. Tests are useful to ensure your Application passes Primary Consideration A.
You may use common libraries in your project such as Apache Commons and Google Guava, particularly if their use helps
improve Application simplicity and readability. However the use of large frameworks, such as Akka, is prohibited.
Your Application may not for any part of its operation use or require the use of external systems, for example Apache
Kafka or Redis.
At your discretion, leading zeroes present in the input may be stripped—or not used—when writing output to the log or
console.
Robust implementations of the Application typically handle more than 2M numbers per 10-second reporting period on a
modern MacBook Pro laptop (e.g.: 16 GiB of RAM and a 2.5 GHz Intel i7 processor).
