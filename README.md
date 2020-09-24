# AutoApplier
> Java 8 + 11 | Easy UI & UX

## Preamble
I found that certain billing or templating software/ services charge too much and while logically swapping out variables for values (provided by the user) is a simple task, the pricing is atrocious.
Or if you have been searching for a templating software, this is for you.

AutoApplier was born out of my own need of templating _(a lot more than I'd like to lmao)_ and will remain entirely OpenSource to provide new coders or well-versed people a resource for others to learn from or improve or even build upon.

If you have any suggestions please do create an issue with appropriate tagging!
If you have feedback or just want to shoutout, hit me up on Twitter @saadat603 :-)

## Features
- Complete Word (2007+) Templating Support
	- Variables in text or tables, both work!
- Support for custom presets
	- You can view the example `test.aaconf` to see how it works!
- Multilingual/ i18n Support _(coming soon, read below)_

## Installation & Download
#### Info:
AutoApplier comes as _shaded_ JAR, meaning it includes all the necessary JavaFX components so you don't have to add anything or provide anything at all in that regard. 

#### Prerequesites:
Java Runtime 11 or 8, depending on your case.

Click or Tap [HERE](d) to download the **Java 11** Version. (Compiled with AdoptOpenJDK 11)

Click or Tap [HERE](d) to download the **Java 8** Version. (Compiled with AdoptOpenJDK 8)
 

## Future/ Planned Features
- Multilingual support
	-  General idea is to have a dedicated langauge file with the respective strings translated and loaded at runtime. A Settings panel would need to be implemented as well.
- Auto-Conversion to PDF

## F.A.Q
#### *Q: How does it work \*exactly\*???*
A: It uses docx4j underneath for the actualy variable replacement and a custom datatstructure to store the replacement key-value pairs!

#### *Q: I see you're using TWO LISTS instead of a List of Pairs (JavaFX11+) ? So inefficient!!1!11*
A: No, it's really not. I could've tried making my double list management multi-threaded but the speed's of ArrayLists arn't bad.

-	Adding: O(1+1) (worst case O(2n) if indexed adding)
-	Deletion: O(2n)
-	Search: O(2n)
-	Get: O(1+1)

For the amount of data I'm expecting this to work with, these speeds are acceptible.

#### *Q: Why is your general-purpose property loader called "PreferenceLaoder" ? It's a property loader!*
A: I have a love for iOS/ Darwin research in my private time and I just happen to choose the name of the jailbroken user's custom Preference Panel loader: PreferenceLoader!


## Credits:
- docx4j Team for their amazing work
- OpenJFX for maintaining JavaFX
- JetBrains for their amazing IDE
- My swedish pigeon gang for the true support™️
- My friends and family
