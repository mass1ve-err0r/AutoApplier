# AutoApplier
> Java 8 + 11 | Easy UI & UX

## Preamble
I found that certain billing or templating software/ services charge too much and while logically swapping out variables for values (provided by the user) is a simple task, the pricing is atrocious.

Or if you have been searching for a templating software, this is for you.

AutoApplier was born out of my own need of templating _(a lot more than I'd like to lmao)_ and will remain entirely OpenSource to provide new coders or well-versed people a resource to learn from, improve or even build upon.

If you have any suggestions please do create an issue with appropriate tagging!
If you have feedback or just want to shoutout, hit me up on Twitter @saadat603 :-)

## Features
- Complete Word (2007+) Templating Support
	- Variables in text or tables, both work!
- Support for custom presets
	- You can view the example `test.aaconf` to see how it works!
- Auto-output as docx *and* PDF

## Future / Planned Features
- Multilingual support
	-  General idea is to have a dedicated langauge file with the respective strings translated and loaded at runtime. A Settings panel would need to be implemented as well.
- ~Auto conversion to PDF~ Done

## Installation & Download
#### Info:
AutoApplier comes as _shaded_ JAR, meaning it includes all the necessary JavaFX components so you don't have to add anything or provide anything at all in that regard. 

#### Instructions for self-compiling:
*I am assuming you're using IntelliJ as your IDE of choice for Java.*

1. clone this project (For Java 11 just `git clone https://github.com/mass1ve-err0r/AutoApplier.git`, for Java 8 use `git clone -b Java8 https://github.com/mass1ve-err0r/AutoApplier.git`)
2. Grab a JDK of your choice to build the Project (8 or 11, newer ones aren't recommended due to some potential internal problems, see [THIS](https://stackoverflow.com/a/56064482)).
3. Open it up with intelliJ, open the Maven toolbar and select `Package` from the lifecycle.
4. It should now have built a fully shaded JAR for you inside the `target` folder to use without opening the IDE every time.

At this point you're free to place the application / `JAR` anywhere you like because it's portable for *your system*.

#### Prebuilt Images:
I provide the following *working\** images with their respective specs:

- macOS (10.15.6 Catalina, AdoptOpenJDK 11.0.8) : Click [HERE](x)
- Windows (Win10, AdoptOpenJDK 11.0.8) : Click [HERE](x2)

## F.A.Q
#### *Q: How does it work \*exactly\*???*
A: It uses docx4j underneath for the actualy variable replacement and a custom datatstructure to store the replacement key-value pairs!

#### *Q: I see you're using TWO LISTS instead of a (Array-)List of Pairs (JavaFX 11+) ? So inefficient!!1!11*
A: No, it's really not. I could've tried making my double list management multi-threaded but the speeds of ArrayLists aren't bad.

-	Adding: O(1+1) (worst case O(2n) if indexed adding)
-	Deletion: O(2n)
-	Search: O(2n)
-	Get: O(1+1)

For the amount of data I'm expecting this to work with, these speeds are acceptible.

#### *Q: Why is your general-purpose property loader called "PreferenceLaoder" ? It's a property loader!*
A: I have a love for iOS/ Darwin research in my private time and I just happen to choose the name of the jailbroken user's custom Preference Panel loader: PreferenceLoader!

#### *Q: How do the Java 8 and Java 11 version differ?*
A: In no other way than the dependencies being a tad different since docx4j has two versions and some code refactoring for Java 8 compliance.


## Credits:
- docx4j Team for their amazing work
- Apache 
- JetBrains for their amazing IDE
- My swedish pigeon gang for the true support™️
- My friends and family

## License
This product uses the docx4j library which is licensed under the *Apache Software License 2.0* (ASL v2.0).

The product `AutoApplier` as a whole is hereby licensed under the *GNU Affero General Public License 3.0* (AGPL v3.0).

A copy of the License is included in this repository.
