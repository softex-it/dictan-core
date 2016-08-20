#NAME "Sample DSL Dictionary"
#INDEX_LANGUAGE	"English"
#CONTENTS_LANGUAGE	"English"

trivial card
  Trivially simple card. The body of the card starts with spaces or TABs, that's all.<br/><br/>

trivial card with 1 markup
trivial card with 2{ stress<v>e</v>d word}
trivial card with 3{ <w>abbrev.</w>}
  Trivially simple card. <div class="m3">Very simple.</div>
  <div class="m2">Two left margin tags</div><div class="m2"> in one line.</div> and line with no tags.
  <br/><div class="m2">Math expressions: 5 &#60; 3 &#60; 2, 2 &#60;=&#62; 2</div>
  This is <w>abbr.</w>
  <br/><div class="m0">This is m0 line 1</div>
  <div class="m0">This is m0 line 2</div><div class="m0">This is m0 line 3</div>
  <span class="cd">Text with default highlighted color</span>
  <br/><span class="ca" style="color:blue">Text with blue highlighted color</span>
  <br/><t>This is transcription</t>
  <br/>This is <o>&#91;</o> <t>transcription with braces</t> <o>&#93;</o> and text after
  <br/>Stress<v>e</v>d word
  <br/><c>This text is a comment</c>
  <br/><n><f>Translation zone line</f></n>
  <br/><m>Don't index this line</m>
  <br/><e>Example line</e>
  <br/><e testatr>Example line with test attribute</e>
  <br/><l id=1033>Language 1033 line</l>
  <br/><l>Language line with no id</l>
  <br/>Line with braces &#40;&#41; &#123; &#125;
  <br/>One more line.
  <br/>&#160;<br/><br/>
  
specialchars
specialcharssecond
	Escaped:
		<br/>
		<br/>&#160;
		<br/>&#47;
		<br/>&#91;
		<br/>&#93;
		<br/>&#123;
		<br/>&#125;
		<br/>Comments included in &#123;&#123;&#125;&#125; are invisible: <!--DSL comments that are invisible-->
		<br/>&#64; (At sign)
		<br/>\^ (Circumflex accent)
		<br/>&#126; (Tilde)
		<br/>\_
		<br/>\+
		<br/>\-
		<br/>\%
		<br/>\$
		<br/>\&#38;
		<br/>\*
		<br/>\&#60;
		<br/>\&#62;
		<br/>\!
		<br/>\?
		<br/>\# (Hash sign)
		<br/>
	<br/>Unescaped:
	<br/>
	<br/>
		<br/>Escape is not shown \
		<br/>Empty square brackets break the view (not included)
		<br/>Curly braces are shown the same way { }.
		<br/>Comments included in &#123;&#123;&#125;&#125; are invisible: <!--DSL comments that are invisible-->
		<br/>At sign breaks the view (not included)
		<br/>Circumflex accent is shown the same way ^
		<br/>Tilde is replaced by the headword ~
		<br/>_
		<br/>+
		<br/>-
		<br/>%
		<br/>$
		<br/>&#38;
		<br/>*
		<br/>&#60;
		<br/>&#62;
		<br/>!
		<br/>?
		<br/>Hash sign breaks the view (not included)<br/><br/>

little with subentries, since lingvo x3
   1) small
   <br/><div class="m1"><f>
   <br/>@ little one
   <br/>baby
   <br/>@
   <br/></f></div>
   2) tiny, short
   <br/>3) trifle
   <br/><div class="m1"><f>
   <br/>@ little by little
   <br/>gradually
   <br/>@
   <br/></f></div><br/>
   
-ae1
-ae1 {duplicate}
-ae2 {with the long name in the article}
-ae2 with \{braces\}
-ae2 with \{braces\} and {optional part}
-ae2 with \(parentheses\)
-ae2(changing)
-ae2(changing) two (times)
-ae2(changing) two (times) with {optional \(escaped\)} and \(escaped\) 
-ae2 {CO<sub>}2{</sub>}
-ae3 {with the long name in the article} partially {supposed to be} excluded
   <div class="m1"><i><span class="cd"><n>suffix</n></span></i></div>
   <div class="m1">1) <n>used in names of animal and plant families and other groups</n></div>
   <div class="m1"><f><n><e>Felidae</e></n></f></div>
   <div class="m1"><f><n><e>Gymnospermae</e></n></f></div>
   <div class="m1">2) <n>used instead of <i>-as</i> in the plural of many nonnaturalized or unfamiliar nouns ending in <i>-a</i> derived from Latin or Greek</n></div>
   <div class="m1"><f><n><e>alumnae</e></n></f></div>
   <div class="m1"><f><n><e>larvae</e></n></f></div>
   <div class="m1"><n>forming plural nouns:</n></div>
   <div class="m1"><f><b>Origin:</b></f></div>
   <div class="m2"><f><c><l id=1033>representing Latin plural, or the Greek plural ending <i>-ai</i> of some nouns</l></c></f></div><br/>

word without optional part 1
	Body of the card 1<br/><br/>

word without optional part 2
	Body of the card 2<br/><br/>

word with optional part { - some optional part}
	<div class="m1">This is the body of the card</div><br/>

sample entry
example
sample {unsorted part} card 1
sample {unsorted part}card 2
sample{unsorted part} card 3
sample {unsorted part} card 4 {at the end}
{the }sample headword
   •• <b>Special symbols that might require escaping:</b>
   <br/>1. Tilde: I love this &#126;. • I love this ~. (The tilde replaces the first headword.)
   <br/>2. At-sign: &#64; (<b>Note:</b> Sub-entries)
   <br/>3. Brackets: &#91; &#93; &#123; &#125;
   <br/>&#160;
   <br/>•• <b>Basic formatting:</b>
   <br/>&#91;b&#93;bold text&#91;/b&#93; • <b>bold text</b>
   <br/>&#91;i&#93;italics&#91;/i&#93; • <i>italics</i>
   <br/>&#91;u&#93;underlined text&#91;/u&#93; • <u>underlined text</u>
   <br/>CO&#91;sub&#93;2&#91;/sub&#93; • CO<sub>2</sub> • subscript
   <br/>e=mc&#91;sup&#93;2&#91;/sup&#93; • e=mc<sup>2</sup> • superscript
   <br/>&#160;
   <br/>•• <b>Indents:</b>
   <br/><div class="m0">&#91;m0&#93; - sets the left paragraph margin to 0 spaces The corresponding closing tag of the paragraph is. &#91;/m&#93;</div>
   <div class="m1">&#91;m1&#93; - sets the left paragraph margin to 1 spaces The corresponding closing tag of the paragraph is. &#91;/m&#93;</div>
   <div class="m2">&#91;m2&#93; - sets the left paragraph margin to 2 spaces The corresponding closing tag of the paragraph is. &#91;/m&#93;</div>
   <div class="m3">&#91;m3&#93; - sets the left paragraph margin to 3 spaces The corresponding closing tag of the paragraph is. &#91;/m&#93;</div>
   <div class="m4">&#91;m4&#93; - sets the left paragraph margin to 4 spaces The corresponding closing tag of the paragraph is. &#91;/m&#93;</div>
   <div class="m5">&#91;m5&#93; - sets the left paragraph margin to 5 spaces The corresponding closing tag of the paragraph is. &#91;/m&#93;</div>
   <div class="m6">&#91;m6&#93; - sets the left paragraph margin to 6 spaces The corresponding closing tag of the paragraph is. &#91;/m&#93;</div>
   <div class="m7">&#91;m7&#93; - sets the left paragraph margin to 7 spaces The corresponding closing tag of the paragraph is. &#91;/m&#93;</div>
   <div class="m8">&#91;m8&#93; - sets the left paragraph margin to 8 spaces The corresponding closing tag of the paragraph is. &#91;/m&#93;</div>
   <div class="m9">&#91;m9&#93; - sets the left paragraph margin to 9 spaces The corresponding closing tag of the paragraph is. &#91;/m&#93;</div>
   &#160;
   <br/>•• <b>Zones:</b>
   <br/>&#123;&#123;Comments are inside curly brackets&#125;&#125; • <!--This is a comment which should be invisible.-->
   <br/>&#91;*&#93;&#91;ex&#93;example&#91;/ex&#93;&#91;/*&#93; • <f><e>example</e></f> (Lingvo specific: It is recommended to add &#91;*&#93; and &#91;/*&#93; around example).
   <br/>&#91;s&#93;sample.bmp&#91;/s&#93; • <r>sample.bmp</r> • multimedia zone (used to add pictures or sound files into a dictionary entries ).
   <br/>&#91;p&#93;v.&#91;/p&#93; • <w>v.</w> • labels are shown in a different colour
   <br/>str&#91;'&#93;e&#91;/'&#93;ss  • str<v>e</v>ss • A str<v>e</v>ssed v<v>o</v>wel in a w<v>o</v>rd.
   <br/>&#160;
   <br/>•• <b>Links:</b>
   <br/>\<a class="v2" href="typcial card\">typcial card\</a> • <a class="v2" href="typcial card">typcial card</a> • Recommended way to create a hyperlink to another card.
   <br/>&#91;ref&#93;link1&#91;/ref&#93; • <a href="link1">link1</a>  • hyperlink to another card, not recommended, deprecated.
   <br/>&#91;url&#93;http://sample.org/&#91;/url&#93; • <a class="v3" href="http://sample.org/">http://sample.org/</a> • link to a Web page
   <br/>&#160;
   <br/>•• <b>Colors:</b>
   <br/>&#91;c blue&#93;Blue text&#91;/c&#93; • <span class="ca" style="color:blue">Blue text</span>
   <br/>&#160;  <!-- Here's how you insert an empty space: slash and space after it -->
   <br/>Supported colour names:
   <br/>aliceblue: <span class="ca" style="color:aliceblue">plain <u>underscore</u> <i>italic</i> <b>aliceblue</span></b>
   <br/>antiquewhite: <span class="ca" style="color:antiquewhite">plain <u>underscore</u> <i>italic</i> <b>antiquewhite</span></b>
   <br/>aqua: <span class="ca" style="color:cyan">plain <u>underscore</u> <i>italic</i> <b>aqua</span></b>
   <br/>aquamarine: <span class="ca" style="color:aquamarine">plain <u>underscore</u> <i>italic</i> <b>aquamarine</span></b>
   <br/>azure: <span class="ca" style="color:azure">plain <u>underscore</u> <i>italic</i> <b>azure</span></b>
   <br/>beige: <span class="ca" style="color:beige">plain <u>underscore</u> <i>italic</i> <b>beige</span></b>
   <br/>bisque: <span class="ca" style="color:bisque">plain <u>underscore</u> <i>italic</i> <b>bisque</span></b>
   <br/>blanchedalmond: <span class="ca" style="color:blanchedalmond">plain <u>underscore</u> <i>italic</i> <b>blanchedalmond</span></b>
   <br/>blue: <span class="ca" style="color:blue">plain <u>underscore</u> <i>italic</i> <b>blue</span></b>
   <br/>blueviolet: <span class="ca" style="color:blueviolet">plain <u>underscore</u> <i>italic</i> <b>blueviolet</span></b>
   <br/>brown: <span class="ca" style="color:brown">plain <u>underscore</u> <i>italic</i> <b>brown</span></b>
   <br/>burlywood: <span class="ca" style="color:burlywood">plain <u>underscore</u> <i>italic</i> <b>burlywood</span></b>
   <br/>cadetblued: <span class="ca" style="color:cadetblue">plain <u>underscore</u> <i>italic</i> <b>cadetblue</span></b>
   <br/>chartreuse: <span class="ca" style="color:chartreuse">plain <u>underscore</u> <i>italic</i> <b>chartreuse</span></b>
   <br/>chocolate: <span class="ca" style="color:chocolate">plain <u>underscore</u> <i>italic</i> <b>chocolate</span></b>
   <br/>coral: <span class="ca" style="color:coral">plain <u>underscore</u> <i>italic</i> <b>coral</span></b>
   <br/>cornflower: <span class="ca" style="color:cornflower">plain <u>underscore</u> <i>italic</i> <b>cornflower</span></b>
   <br/>cornsilk: <span class="ca" style="color:cornsilk">plain <u>underscore</u> <i>italic</i> <b>cornsilk</span></b>
   <br/>crimson: <span class="ca" style="color:crimson">plain <u>underscore</u> <i>italic</i> <b>crimson</span></b>
   <br/>cyan: <span class="ca" style="color:cyan">plain <u>underscore</u> <i>italic</i> <b>cyan</span></b>
   <br/>darkblue: <span class="ca" style="color:darkblue">plain <u>underscore</u> <i>italic</i> <b>darkblue</span></b>
   <br/>darkcyan: <span class="ca" style="color:darkcyan">plain <u>underscore</u> <i>italic</i> <b>darkcyan</span></b>
   <br/>darkgoldenrod: <span class="ca" style="color:darkgoldenrod">plain <u>underscore</u> <i>italic</i> <b>darkgoldenrod</span></b>
   <br/>darkgray: <span class="ca" style="color:darkgray">plain <u>underscore</u> <i>italic</i> <b>darkgray</span></b>
   <br/>darkgreen: <span class="ca" style="color:darkgreen">plain <u>underscore</u> <i>italic</i> <b>darkgreen</span></b>
   <br/>darkkhaki: <span class="ca" style="color:darkkhaki">plain <u>underscore</u> <i>italic</i> <b>darkkhaki</span></b>
   <br/>darkmagenta: <span class="ca" style="color:darkmagenta">plain <u>underscore</u> <i>italic</i> <b>darkmagenta</span></b>
   <br/>darkolivegreen: <span class="ca" style="color:darkolivegreen">plain <u>underscore</u> <i>italic</i> <b>darkolivegreen</span></b>
   <br/>darkorange: <span class="ca" style="color:darkorange">plain <u>underscore</u> <i>italic</i> <b>darkorange</span></b>
   <br/>darkorchid: <span class="ca" style="color:darkorchid">plain <u>underscore</u> <i>italic</i> <b>darkorchid</span></b>
   <br/>darkred: <span class="ca" style="color:darkred">plain <u>underscore</u> <i>italic</i> <b>darkred</span></b>
   <br/>darksalmon: <span class="ca" style="color:darksalmon">plain <u>underscore</u> <i>italic</i> <b>darksalmon</span></b>
   <br/>darkseagreen: <span class="ca" style="color:darkseagreen">plain <u>underscore</u> <i>italic</i> <b>darkseagreen</span></b>
   <br/>darkslateblue: <span class="ca" style="color:darkslateblue">plain <u>underscore</u> <i>italic</i> <b>darkslateblue</span></b>
   <br/>darkslategray: <span class="ca" style="color:darkslategray">plain <u>underscore</u> <i>italic</i> <b>darkslategray</span></b>
   <br/>darkturquoise: <span class="ca" style="color:darkturquoise">plain <u>underscore</u> <i>italic</i> <b>darkturquoise</span></b>
   <br/>darkviolet: <span class="ca" style="color:darkviolet">plain <u>underscore</u> <i>italic</i> <b>darkviolet</span></b>
   <br/>deeppink: <span class="ca" style="color:deeppink">plain <u>underscore</u> <i>italic</i> <b>deeppink</span></b>
   <br/>deepskyblue: <span class="ca" style="color:deepskyblue">plain <u>underscore</u> <i>italic</i> <b>deepskyblue</span></b>
   <br/>dimgray: <span class="ca" style="color:dimgray">plain <u>underscore</u> <i>italic</i> <b>dimgray</span></b>
   <br/>dodgerblue: <span class="ca" style="color:dodgerblue">plain <u>underscore</u> <i>italic</i> <b>dodgerblue</span></b>
   <br/>firebrick: <span class="ca" style="color:firebrick">plain <u>underscore</u> <i>italic</i> <b>firebrick</span></b>
   <br/>floralwhite: <span class="ca" style="color:floralwhite">plain <u>underscore</u> <i>italic</i> <b>floralwhite</span></b>
   <br/>forestgreen: <span class="ca" style="color:forestgreen">plain <u>underscore</u> <i>italic</i> <b>forestgreen</span></b>
   <br/>fuchsia: <span class="ca" style="color:magenta">plain <u>underscore</u> <i>italic</i> <b>fuchsia</span></b>
   <br/>gainsboro: <span class="ca" style="color:gainsboro">plain <u>underscore</u> <i>italic</i> <b>gainsboro</span></b>
   <br/>ghostwhite: <span class="ca" style="color:ghostwhite">plain <u>underscore</u> <i>italic</i> <b>ghostwhite</span></b>
   <br/>gold: <span class="ca" style="color:gold">plain <u>underscore</u> <i>italic</i> <b>gold</span></b>
   <br/>goldenrod: <span class="ca" style="color:goldenrod">plain <u>underscore</u> <i>italic</i> <b>goldenrod</span></b>
   <br/>gray: <span class="ca" style="color:gray">plain <u>underscore</u> <i>italic</i> <b>gray</span></b>
   <br/>green: <span class="ca" style="color:green">plain <u>underscore</u> <i>italic</i> <b>green</span></b>
   <br/>greenyellow: <span class="ca" style="color:greenyellow">plain <u>underscore</u> <i>italic</i> <b>greenyellow</span></b>
   <br/>honeydew: <span class="ca" style="color:honeydew">plain <u>underscore</u> <i>italic</i> <b>honeydew</span></b>
   <br/>hotpink: <span class="ca" style="color:hotpink">plain <u>underscore</u> <i>italic</i> <b>hotpink</span></b>
   <br/>indianred: <span class="ca" style="color:indianred">plain <u>underscore</u> <i>italic</i> <b>indianred</span></b>
   <br/>indigo: <span class="ca" style="color:indigo">plain <u>underscore</u> <i>italic</i> <b>indigo</span></b>
   <br/>ivory: <span class="ca" style="color:ivory">plain <u>underscore</u> <i>italic</i> <b>ivory</span></b>
   <br/>khaki: <span class="ca" style="color:khaki">plain <u>underscore</u> <i>italic</i> <b>khaki</span></b>
   <br/>lavender: <span class="ca" style="color:lavender">plain <u>underscore</u> <i>italic</i> <b>lavender</span></b>
   <br/>lavenderblush: <span class="ca" style="color:lavenderblush">plain <u>underscore</u> <i>italic</i> <b>lavenderblush</span></b>
   <br/>lawngreen: <span class="ca" style="color:lawngreen">plain <u>underscore</u> <i>italic</i> <b>lawngreen</span></b>
   <br/>lemonchiffon: <span class="ca" style="color:lemonchiffon">plain <u>underscore</u> <i>italic</i> <b>lemonchiffon</span></b>
   <br/>lightblue: <span class="ca" style="color:lightblue">plain <u>underscore</u> <i>italic</i> <b>lightblue</span></b>
   <br/>lightcoral: <span class="ca" style="color:lightcoral">plain <u>underscore</u> <i>italic</i> <b>lightcoral</span></b>
   <br/>lightcyan: <span class="ca" style="color:lightcyan">plain <u>underscore</u> <i>italic</i> <b>lightcyan</span></b>
   <br/>lightgoldenrodyellow: <span class="ca" style="color:lightgoldenrodyellow">plain <u>underscore</u> <i>italic</i> <b>lightgoldenrodyellow</span></b>
   <br/>lightgreen: <span class="ca" style="color:lightgreen">plain <u>underscore</u> <i>italic</i> <b>lightgreen</span></b>
   <br/>lightgray: <span class="ca" style="color:lightgray">plain <u>underscore</u> <i>italic</i> <b>lightgray</span></b>
   <br/>lightpink: <span class="ca" style="color:lightpink">plain <u>underscore</u> <i>italic</i> <b>lightpink</span></b>
   <br/>lightsalmon: <span class="ca" style="color:lightsalmon">plain <u>underscore</u> <i>italic</i> <b>lightsalmon</span></b>
   <br/>lightseagreen: <span class="ca" style="color:lightseagreen">plain <u>underscore</u> <i>italic</i> <b>lightseagreen</span></b>
   <br/>lightskyblue: <span class="ca" style="color:lightskyblue">plain <u>underscore</u> <i>italic</i> <b>lightskyblue</span></b>
   <br/>lightslategray: <span class="ca" style="color:lightslategray">plain <u>underscore</u> <i>italic</i> <b>lightslategray</span></b>
   <br/>lightsteelblue: <span class="ca" style="color:lightsteelblue">plain <u>underscore</u> <i>italic</i> <b>lightsteelblue</span></b>
   <br/>lightyellow: <span class="ca" style="color:lightyellow">plain <u>underscore</u> <i>italic</i> <b>lightyellow</span></b>
   <br/>lime: <span class="ca" style="color:lime">plain <u>underscore</u> <i>italic</i> <b>lime</span></b>
   <br/>limegreen: <span class="ca" style="color:limegreen">plain <u>underscore</u> <i>italic</i> <b>limegreen</span></b>
   <br/>linen: <span class="ca" style="color:linen">plain <u>underscore</u> <i>italic</i> <b>linen</span></b>
   <br/>magenta: <span class="ca" style="color:magenta">plain <u>underscore</u> <i>italic</i> <b>magenta</span></b>
   <br/>maroon: <span class="ca" style="color:maroon">plain <u>underscore</u> <i>italic</i> <b>maroon</span></b>
   <br/>mediumaquamarine: <span class="ca" style="color:mediumaquamarine">plain <u>underscore</u> <i>italic</i> <b>mediumaquamarine</span></b>
   <br/>mediumblue: <span class="ca" style="color:mediumblue">plain <u>underscore</u> <i>italic</i> <b>mediumblue</span></b>
   <br/>mediumorchid: <span class="ca" style="color:mediumorchid">plain <u>underscore</u> <i>italic</i> <b>mediumorchid</span></b>
   <br/>mediumpurple: <span class="ca" style="color:mediumpurple">plain <u>underscore</u> <i>italic</i> <b>mediumpurple</span></b>
   <br/>mediumseagreen: <span class="ca" style="color:mediumseagreen">plain <u>underscore</u> <i>italic</i> <b>mediumseagreen</span></b>
   <br/>mediumslateblue: <span class="ca" style="color:mediumslateblue">plain <u>underscore</u> <i>italic</i> <b>mediumslateblue</span></b>
   <br/>mediumspringgreen: <span class="ca" style="color:mediumspringgreen">plain <u>underscore</u> <i>italic</i> <b>mediumspringgreen</span></b>
   <br/>mediumturquoise: <span class="ca" style="color:mediumturquoise">plain <u>underscore</u> <i>italic</i> <b>mediumturquoise</span></b>
   <br/>mediumvioletred: <span class="ca" style="color:mediumvioletred">plain <u>underscore</u> <i>italic</i> <b>mediumvioletred</span></b>
   <br/>midnightblue: <span class="ca" style="color:midnightblue">plain <u>underscore</u> <i>italic</i> <b>midnightblue</span></b>
   <br/>mintcream: <span class="ca" style="color:mintcream">plain <u>underscore</u> <i>italic</i> <b>mintcream</span></b>
   <br/>mistyrose: <span class="ca" style="color:mistyrose">plain <u>underscore</u> <i>italic</i> <b>mistyrose</span></b>
   <br/>moccasin: <span class="ca" style="color:moccasin">plain <u>underscore</u> <i>italic</i> <b>moccasin</span></b>
   <br/>navajowhite: <span class="ca" style="color:navajowhite">plain <u>underscore</u> <i>italic</i> <b>navajowhite</span></b>
   <br/>navy: <span class="ca" style="color:navy">plain <u>underscore</u> <i>italic</i> <b>navy</span></b>
   <br/>oldlace: <span class="ca" style="color:oldlace">plain <u>underscore</u> <i>italic</i> <b>oldlace</span></b>
   <br/>olive: <span class="ca" style="color:olive">plain <u>underscore</u> <i>italic</i> <b>olive</span></b>
   <br/>olivedrab: <span class="ca" style="color:olivedrab">plain <u>underscore</u> <i>italic</i> <b>olivedrab</span></b>
   <br/>orange: <span class="ca" style="color:orange">plain <u>underscore</u> <i>italic</i> <b>orange</span></b>
   <br/>orangered: <span class="ca" style="color:orangered">plain <u>underscore</u> <i>italic</i> <b>orangered</span></b>
   <br/>orchid: <span class="ca" style="color:orchid">plain <u>underscore</u> <i>italic</i> <b>orchid</span></b>
   <br/>palegoldenrod: <span class="ca" style="color:palegoldenrod">plain <u>underscore</u> <i>italic</i> <b>palegoldenrod</span></b>
   <br/>palegreen: <span class="ca" style="color:palegreen">plain <u>underscore</u> <i>italic</i> <b>palegreen</span></b>
   <br/>paleturquoise: <span class="ca" style="color:paleturquoise">plain <u>underscore</u> <i>italic</i> <b>paleturquoise</span></b>
   <br/>palevioletred: <span class="ca" style="color:palevioletred">plain <u>underscore</u> <i>italic</i> <b>palevioletred</span></b>
   <br/>papayawhip: <span class="ca" style="color:papayawhip">plain <u>underscore</u> <i>italic</i> <b>papayawhip</span></b>
   <br/>peachpuff: <span class="ca" style="color:peachpuff">plain <u>underscore</u> <i>italic</i> <b>peachpuff</span></b>
   <br/>peru: <span class="ca" style="color:peru">plain <u>underscore</u> <i>italic</i> <b>peru</span></b>
   <br/>pink: <span class="ca" style="color:pink">plain <u>underscore</u> <i>italic</i> <b>pink</span></b>
   <br/>plum: <span class="ca" style="color:plum">plain <u>underscore</u> <i>italic</i> <b>plum</span></b>
   <br/>powderblue: <span class="ca" style="color:powderblue">plain <u>underscore</u> <i>italic</i> <b>powderblue</span></b>
   <br/>purple: <span class="ca" style="color:purple">plain <u>underscore</u> <i>italic</i> <b>purple</span></b>
   <br/>red: <span class="ca" style="color:red">plain <u>underscore</u> <i>italic</i> <b>red</span></b>
   <br/>rosybrown: <span class="ca" style="color:rosybrown">plain <u>underscore</u> <i>italic</i> <b>rosybrown</span></b>
   <br/>royalblue: <span class="ca" style="color:royalblue">plain <u>underscore</u> <i>italic</i> <b>royalblue</span></b>
   <br/>saddlebrown: <span class="ca" style="color:saddlebrown">plain <u>underscore</u> <i>italic</i> <b>saddlebrown</span></b>
   <br/>salmon: <span class="ca" style="color:salmon">plain <u>underscore</u> <i>italic</i> <b>salmon</span></b>
   <br/>sandybrown: <span class="ca" style="color:sandybrown">plain <u>underscore</u> <i>italic</i> <b>sandybrown</span></b>
   <br/>seagreen: <span class="ca" style="color:seagreen">plain <u>underscore</u> <i>italic</i> <b>seagreen</span></b>
   <br/>seashell: <span class="ca" style="color:seashell">plain <u>underscore</u> <i>italic</i> <b>seashell</span></b>
   <br/>sienna: <span class="ca" style="color:sienna">plain <u>underscore</u> <i>italic</i> <b>sienna</span></b>
   <br/>silver: <span class="ca" style="color:silver">plain <u>underscore</u> <i>italic</i> <b>silver</span></b>
   <br/>skyblue: <span class="ca" style="color:skyblue">plain <u>underscore</u> <i>italic</i> <b>skyblue</span></b>
   <br/>slateblue: <span class="ca" style="color:slateblue">plain <u>underscore</u> <i>italic</i> <b>slateblue</span></b>
   <br/>slategray: <span class="ca" style="color:slategray">plain <u>underscore</u> <i>italic</i> <b>slategray</span></b>
   <br/>snow: <span class="ca" style="color:snow">plain <u>underscore</u> <i>italic</i> <b>snow</span></b>
   <br/>springgreen: <span class="ca" style="color:springgreen">plain <u>underscore</u> <i>italic</i> <b>springgreen</span></b>
   <br/>steelblue: <span class="ca" style="color:steelblue">plain <u>underscore</u> <i>italic</i> <b>steelblue</span></b>
   <br/>tan: <span class="ca" style="color:tan">plain <u>underscore</u> <i>italic</i> <b>tan</span></b>
   <br/>teal: <span class="ca" style="color:teal">plain <u>underscore</u> <i>italic</i> <b>teal</span></b>
   <br/>thistle: <span class="ca" style="color:thistle">plain <u>underscore</u> <i>italic</i> <b>thistle</span></b>
   <br/>tomato: <span class="ca" style="color:tomato">plain <u>underscore</u> <i>italic</i> <b>tomato</span></b>
   <br/>turquoise: <span class="ca" style="color:turquoise">plain <u>underscore</u> <i>italic</i> <b>turquoise</span></b>
   <br/>violet: <span class="ca" style="color:violet">plain <u>underscore</u> <i>italic</i> <b>violet</span></b>
   <br/>wheat: <span class="ca" style="color:wheat">plain <u>underscore</u> <i>italic</i> <b>wheat</span></b>
   <br/>white: <span class="ca" style="color:white">plain <u>underscore</u> <i>italic</i> <b>white</span></b>
   <br/>whitesmoke: <span class="ca" style="color:whitesmoke">plain <u>underscore</u> <i>italic</i> <b>whitesmoke</span></b>
   <br/>yellow: <span class="ca" style="color:yellow">plain <u>underscore</u> <i>italic</i> <b>yellow</span></b>
   <br/>yellowgreen: <span class="ca" style="color:yellowgreen">plain <u>underscore</u> <i>italic</i> <b>yellowgreen</span></b><br/><br/>

typical card
   This is a typical card taken from Lingvo Documentation:
   <br/>&#160;
   <br/><b>1.</b> <w>v.</w>
   <br/><div class="m1">1) <w><n>common.</w> refuse <c>(<i>from something</i>)</c>, stop <c>(<i>something, doing something</i>)</c>; leave <c>(<i>something</i>)</c></n></div>
   <div class="m2"><f><e><l id=1033>to abandon a Bill</l> — refuse to move with a law project</e></f></div>
   <div class="m2"><f><e><l id=1033>to abandon a claim &#91;a right, an action&#93;</l> — refuse claims &#91;of rights, of civil actions&#93;</e></f></div>
   <div class="m2"><f><e><l id=1033>to abandon (all) hope</l> — leave (any) hope</e></f></div>
   <div class="m2"><f><b>See:</b></f></div>
   <div class="m2"><f><c><l id=1033>abandoned property</l></c></f></div>
   <div class="m1">2) <w><n>ec.</w> закрывать; консервировать <c>(<i>напр., транспортную линию, производство и т. п.</i>)</c></n></div>
   <div class="m2"><f><e><l id=1033>to abandon the transportation services</l> — заморозить транспортное обслуживание</e></f></div>
   <div class="m1">3) <w><n>общ.</w> оставлять, уходить <c>(<i>с поста и т. д.</i>)</c>; покидать, оставлять <c>(<i>что-л. или кого-л.</i>)</c></n></div>
   <div class="m2"><f><e><l id=1033>to be forced to abandon a position</l> — быть вынужденным оставить должность</e></f></div>
   <div class="m2"><f><e><l id=1033>He abandoned his family and went abroad.</l> — Он оставил свою семью и поехал за границу.</e></f></div>
   <div class="m2"><f><e><l id=1033>He abandoned his car and tried to escape on foot.</l> — Он бросил машину и попытался ускользнуть пешком.</e></f></div>
   <div class="m2"><f><b>See:</b></f></div>
   <div class="m2"><f><a href="abandonment" dict="Marketing (En-Ru)">abandonment</a></f></div>
   <b>2.</b> <w>noun.</w>
   <br/><div class="m1"><w><n>abst.</w> импульсивность; развязанность, несдержанность</n></div>
   <div class="m2"><f><e><l id=1033>to do smth. with in complete abandon</l> — делать что-л., совершенно забыв обо всем</e></f></div><br/>

human embryology
	<div class="m1">&#160;</div>
	<div class="m0"><span class="ca" style="color:darkmagenta"><b>▪ biology</b></span></div>
	<div class="m1"><!--2--><i><b><span class="ca" style="color:darkcyan">Introduction</b></i></span></div>
	<div class="m1">&#160;</div>
	<div class="m1">      the process encompassing the period from the formation of an embryo, through the development of a fetus, to birth.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      The  <a class="v2" href="human body">human body</a>, like that of most animals, develops from a single cell produced by the union of a male sex cell and a female sex cell. Human development follows closely the basic vertebrate pattern, and it departs only in certain details from the type specifically characteristic of mammals. A prenatal period, in which most of the developmental advances occur, is followed by a long postnatal period. Only at about the age of 25 are the last progressive changes completed.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--2--><i><b><span class="ca" style="color:darkcyan">Normal development</b></i></span></div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--3--><b><span class="ca" style="color:cadetblue">From fertilization to placentation</b></span></div>
	<div class="m1"><!--4--><b> <a class="v2" href="fertilization">fertilization</a></b></div>
	<div class="m1">      The development and liberation of the male and female gametes (<a class="v2" href="gamete">gamete</a>) are steps preparatory to their union through the process of fertilization. Random movements first bring some spermatozoa into contact with follicle cells adhering to the secondary oocyte, which still lies high in the uterine tube. The sperm then propel themselves past the follicle cells and attach to the surface of the gelatinous zona pellucida enclosing the oocyte. Some sperm heads successfully penetrate this capsule by means of an enzyme they secrete,  <a class="v2" href="hyaluronidase">hyaluronidase</a>, but only one sperm makes contact with the cell membrane and cytoplasm of the oocyte and proceeds farther. This is because the invading sperm head releases a substance that initiates surface changes in the oocyte cytoplasm that other competitors cannot master.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      The successful sperm is engulfed by a conical protrusion of the oocyte cytoplasm and is drawn inward. Once within the periphery of the oocyte, the sperm advances toward the centre of the cytoplasm; the head swells and converts into a typical nucleus, now called the male pronucleus, and the tail detaches. It is during the progress of these events that the oocyte initiates its final maturation division. Following the separation of the second polar body, the oocyte nucleus reconstitutes typically and is then called the female pronucleus of the ripe egg. It is now ready to unite with its male counterpart and thereby consummate the total events of fertilization.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      The two pronuclei next approach, meet midway in the egg cytoplasm, and lose their nuclear membranes. Each resolves its diffuse chromatin material into a complete, single set of 23 chromosomes (<a class="v2" href="chromosome">chromosome</a>). Centrioles (complex particles involved in cell division), apparently supplied by the sperm, appear, and a mitotic spindle organizes with the two sets of chromosomes arranged midway on it—ready to proceed with a typical  <a class="v2" href="mitosis">mitosis</a>. This climax in the events of fertilization creates a joint product named the zygote. It contains all the essential factors for the development of a new individual.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      The fundamental results of fertilization are the following: (1) reassociation of a male and female set of chromosomes (thus restoring the full number and providing the basis for biparental inheritance and for variation); (2) establishment of the mechanism of sex determination for the new individual (this depending on whether the male set of chromosomes included the X or the Y chromosome); (3) activation of the zygote, initiating a beginning toward the production of a new individual.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--4--><b> <a class="v2" href="cleavage">cleavage</a> and blastulation</b></div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--5--><b>Cleavage</b></div>
	<div class="m1"><r>I1077.jpg</r> <i></i>The onset of  <a class="v2" href="mitosis">mitosis</a> (ordinary cell proliferation by division) in the activated zygote is a first step toward development in the ordinary sense of that term, and the cells so produced are the first external sign of future body building. To this end, the relatively enormous zygote directly subdivides into many smaller cells of conventional size, suitable as early building units for the future organism. This process is called cleavage and the resulting cells are blastomeres (Figures 1A–D--&#62;<r>I1077.jpg</r>). The tendency for the progressive increase in cell numbers to follow a doubling sequence is soon disturbed and then lost. Each blastomere receives the full complement of paternal and maternal chromosomes.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      Subdivision of the zygote into blastomeres begins while it is still high in the uterine tube. The cohering blastomeres are transported downward chiefly, at least, by muscular contractions of the tubal wall. Such transport is relatively rapid until the lower end of the tube is reached, and here cleavage continues for about two days before the multicellular cluster is expelled into the uterus. The full reason for this delay is not clear, but it serves to retain the cleaving blastomeres until the uterine lining is suitably prepared to receive its prospective guest.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      Since the human egg contains little inert yolk material, and since this is distributed rather evenly throughout the cytoplasm, the daughter cells of each mitosis are practically equal in size and composition. This type of cleavage is known as total, equal cleavage. The sticky blastomeres adhere and the cluster is still retained for a time within the gelatinous capsule—the zona pellucida—that had enclosed the growing and ovulated oocyte. There is no growth in the rapidly dividing blastomeres, so that the total mass of living substance does not increase during the cleavage period.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--5--><b>Morula and blastocyst</b></div>
	<div class="m1"><r>I1077.jpg</r> <i></i><r>I1077.jpg</r> <i></i>By the fourth day after ovulation, a cluster of about 12 blastomeres passes from the uterine tube into the uterus. When the cluster numbers 12 to 16 blastomeres it is called a  <a class="v2" href="morula">morula</a> (Figure 1D--&#62;<r>I1077.jpg</r>). By the time some 30 blastomeres have been produced, pools of clear fluid accumulate between some of the internal cells, and these spaces soon coalesce into a common subcentral cavity. The resulting hollow cellular ball is a blastula of a particular type that occurs in mammals and is called a blastocyst; (<a class="v2" href="blastocyst">blastocyst</a>) its cavity is the blastocoel (Figure 1E, F--&#62;<r>I1077.jpg</r>).</div>
	<div class="m1">&#160;</div>
	<div class="m1">      An internal cellular cluster, eccentric in position and now named the inner cell mass, will develop into the embryo. The external capsule of smaller cells, enveloping the segregated internal cluster, constitutes the trophoblast. It will contribute to the formation of a  <a class="v2" href="placenta">placenta</a>. During its stay within the uterine cavity, the blastocyst loses its gelatinous capsule, imbibes fluid, and expands to a diameter of 0.2 millimetre (0.008 inch); this is nearly twice the diameter of the zygote at the start of cleavage. Probably several hundred blastomeres have formed before the blastocyst attaches to the uterine (<a class="v2" href="uterus">uterus</a>) lining.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--4--><b>Implantation and placentation</b></div>
	<div class="m1"><r>I1077.jpg</r> <i></i>Late in the sixth day after ovulation the naked, sticky blastocyst comes into contact with the uterine lining and adheres to it (Figure 1F--&#62;<r>I1077.jpg</r>). The site of attachment is variable and not predetermined. The uterine lining has already been preparing, under the influence of ovarian hormones, for the reception of the blastocyst. Among these preparations has been the elaboration and expulsion, by the uterine glands, of a secretion that serves as nourishment for the blastocyst, both when it is free and during its implantation. Directly after blastocyst attachment come its establishment within the thickened uterine lining and the participation of its trophoblastic capsule in the differentiation of a placenta, a structure that enables the developing embryo to enter into a prompt physiological dependence on the mother.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--5--><b> <a class="v2" href="implantation">implantation</a></b></div>
	<div class="m1"><r>I1078.jpg</r> <i></i><r>I1078.jpg</r> <i></i>The trophoblast of the blastocyst exerts an enzymic, destructive influence on the swollen uterine lining, leading to erosion of both the superficial  <a class="v2" href="epithelium">epithelium</a> of the uterine lining and also its deeper, cellular connective tissue (Figure 1G--&#62;<r>I1078.jpg</r>). This early stage of invasion ends in a few days; the blastocyst is then completely buried within a more superficial and compact layer of the total uterine lining (Figure 1G, H--&#62;<r>I1078.jpg</r>). While the blastocyst is completing this phase of implantation, its original shell of cellular trophoblast steadily proliferates a multitude of cells that lose their outermost membranes and merge. The result is a thick peripheral layer consisting of a common mass of cytoplasm in which many nuclei are embedded. This external investment is called syncytial trophoblast.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><r>I1078.jpg</r> <i></i><r>I1080.jpg</r> <i></i>The implanted blastocyst next proceeds to establish itself as dependent upon the uterus. The syncytial trophoblast becomes a spongy shell containing irregular cavities (Figure 1H--&#62;<r>I1078.jpg</r>). This expanding mass destroys cellular  <a class="v2" href="connective tissue">connective tissue</a> and glands encountered in its path. Both the cellular and derivative syncytial trophoblast have the capacity of ingesting such tissue. The erosive process also taps capillaries and small blood vessels, and blood liberated in this way is likewise taken up into the trophoblast. Such engulfed blood cells and fragments of tissue are presumably utilized for nourishment in the early period of establishment of the expanding blastocystic sac. Yet these erosive activities decline in intensity by the end of the third week of development, and at this time the sac is completing the first phase of its specialization (Figure 1L--&#62;<r>I1080.jpg</r>).</div>
	<div class="m1">&#160;</div>
	<div class="m1">      Occasionally a fertilized egg fails to reach the uterus, implanting and beginning to develop elsewhere. This outcome is called an ectopic (<a class="v2" href="ectopic pregnancy">ectopic pregnancy</a>) or extra-uterine pregnancy. The most common ectopic site is the uterine tube—this type of pregnancy, if not treated, can be fatal for the mother—but the peritoneum lining the abdominal cavity and even the interior of the ovary are also involved, though rarely. The unsuitability of all these sites for continued development usually leads to early death and resorption of the embryo.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--5--><b>Placentation</b></div>
	<div class="m1"><r>I1078.jpg</r> <i></i><r>I1079.jpg</r> <i></i>The irregular strands of invasive syncytial trophoblast constitute a first stage in the formation of true villi (<a class="v2" href="villus">villus</a>) (which form part of the placenta and are briefly described below). Primitive connective tissue soon lines the interior of the blastocyst wall, and this complex (trophoblast and connective tissue) is then named the  <a class="v2" href="chorion">chorion</a>. Connective tissue promptly grows into the trophoblastic strands, and blood vessels develop in the tissue. The result is the production of many chorionic villi, each resembling a tiny, branching bush (Figure 1I--&#62;<r>I1078.jpg</r>, Ja--&#62;<r>I1079.jpg</r>).</div>
	<div class="m1">&#160;</div>
	<div class="m1"><r>I1081.jpg</r> <i></i>In the fourth week of development, the essential arrangements have been established that make possible those physiological exchanges between mother and fetus that characterize the remainder of pregnancy. The deepest embedded portion of the chorionic wall becomes the so-called chorionic plate of the developing  <a class="v2" href="placenta">placenta</a>. From the plate extend the main stems of chorionic villi, which give off progressively subdividing branches (Figure 1N, P--&#62;<r>I1081.jpg</r>). In general, the side branches are free, whereas apical ones tend to attach to the maternal tissue and serve as anchors. The villous trees occupy a labyrinthine space between the villi that was created by erosion of the uterine lining. Trophoblast not only covers the chorionic plate and its villi but also spreads like a carpet over the eroded surface of the maternal tissue.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      Tapped uterine arteries open into the trophoblast-lined intervillous space, their blood bathing the branches and twigs of the villous trees. This blood drains from the intervillous space through similarly tapped veins. Arterial blood of the embryo, and later fetus, passes through vessels of the umbilical cord to the chorionic plate. Thence it is distributed to the villous stems, branches, and twigs through vessels in their connective-tissue cores. Return of this blood to the fetus is by a reverse route.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      The circulation of maternal blood through the intervillous space is wholly separate from fetal blood coursing through the chorion and its villi. Communication between the two is solely by diffusive interchange. The barrier between the two circulations consists of the trophoblastic covering of villi, the connective tissue of the villous cores, and the thin lining of the capillaries that are contained in the villous cores. The placenta serves the fetus in several ways, most of which involve interchanges of materials carried in the bloodstreams of the mother and fetus. These functions are of the following kinds: (1) nutrition, (2) respiration, (3) excretion, (4) barrier action (e.g., prevention of intrusions by bacteria), and (5) synthesis of hormones and enzymes.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--4--><b>Extra-embryonic membranes (<a class="v2" href="membrane">membrane</a>)</b></div>
	<div class="m1">      The way in which the encapsulating membrane of the blastocyst becomes the chorion, and the most deeply embedded part of it becomes the fetal placenta, has already been described. There are still other important membranes that develop from those portions of the inner cell mass of the blastocyst that are not directly involved in becoming an embryo.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--5--><b>Yolk sac</b></div>
	<div class="m1"><r>I1078.jpg</r> <i></i><r>I1079.jpg</r> <i></i><r>I1080.jpg</r> <i></i>Cells split off from the inner cell mass of the blastocyst and fashion themselves into a primitive yolk sac (Figure 1I--&#62;<r>I1078.jpg</r>, Ja--&#62;<r>I1079.jpg</r>). The roof of the sac then folds into a tubular gut, whereas the remainder becomes a vascularized bag that attains the size of a small pea (Figure 1M--&#62;<r>I1080.jpg</r>). In other vertebrates, such as amphibians and birds, the yolk sac is large and contains a store of nutritive yolk. But in man and other true mammals there is practically none. A slender neck, the yolk stalk, soon connects the rapidly elongating gut with the fast growing yolk sac proper. The stalk detaches from the intestine early in the second month, but the shrunken sac commonly persists and can be found in the expelled afterbirth.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--5--><b> <a class="v2" href="amnion">amnion</a></b></div>
	<div class="m1"><r>I1078.jpg</r> <i></i><r>I1080.jpg</r> <i></i><r>I1081.jpg</r> <i></i><r>I1081.jpg</r> <i></i>A cleft separates the outermost cells of the inner cell mass of the blastocyst from the remainder, which then becomes the embryonic disk (Figure 1G--&#62;<r>I1078.jpg</r>). The split-off, thin upper layer is the  <a class="v2" href="amnion">amnion</a>, which remains attached to the periphery of the embryonic disk. As the disk folds into a cylindrical embryo, the amniotic margin follows the underfolding, and its line of union becomes limited to the ventral (frontward) body wall, where the umbilical cord attaches (Figure 1K--&#62;<r>I1080.jpg</r>, O--&#62;<r>I1081.jpg</r>). The amnion becomes a tough, transparent, nonvascular membrane that gradually fills the chorionic sac and then fuses with it (Figure 1N, P--&#62;<r>I1081.jpg</r>). At the end of the third month of pregnancy, the nonplacental extent of this nearly exposed double membrane comes into contact with the lining of the uterus elsewhere. Fusion then obliterates the uterine cavity, which has been undergoing progressive reduction in size. For the remainder of pregnancy the only cavity within the uterus is that of the fluid-filled amniotic sac.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      Clear, watery fluid fills the amniotic sac. The embryo is suspended in this fluid and thus can maintain its shape and mold its body form without hindrance. Throughout pregnancy the amniotic sac serves as a water cushion, absorbing jolts, equalizing pressures, and permitting the fetus to change posture. At childbirth it acts as a fluid wedge that helps dilate the neck of the uterus. When the sac ruptures, about a quart of fluid escapes as the “waters.” If the sac does not rupture or if it covers the head at birth, it is known as a caul.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--5--><b> <a class="v2" href="allantois">allantois</a></b></div>
	<div class="m1"><r>I1080.jpg</r> <i></i><r>I1081.jpg</r> <i></i>The allantois, a tube of  <a class="v2" href="endoderm">endoderm</a> (the innermost germ layer), grows out of the early yolk sac in a region that soon becomes the hindgut. The tube extends into a bridge of mesoderm (the middle germ layer) that connects embryo with chorion and will become incorporated into the umbilical cord (Figure 1K--&#62;<r>I1080.jpg</r>, O--&#62;<r>I1081.jpg</r>). The human allantoic tube is tiny and never becomes a large sac with important functions, as it does in reptiles, birds, and many other mammals. In the second month it ceases to grow, and it soon is obliterated. Blood vessels, however, develop early in its mesodermal sheath, and these spread into the chorion and vascularize it. Throughout pregnancy they will keep the embryo in close relationship with the mother's uterine circulation.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--5--><b> <a class="v2" href="umbilical cord">umbilical cord</a></b></div>
	<div class="m1"><r>I1081.jpg</r> <i></i><r>I1081.jpg</r> <i></i><r>I1081.jpg</r> <i></i>As the ventral body wall closes in, the yolk stalk and allantois are brought together, along with their mesodermal sheaths and blood vessels (Figure 1K--&#62;<r>I1081.jpg</r>, O--&#62;<r>I1081.jpg</r>). Enclosing everything is a wrapping of amnion. In this manner a cylindrical structure, the umbilical cord, comes to connect the embryo with the placenta (Figure 1N, P--&#62;<r>I1081.jpg</r>). It will serve the embryo and fetus as a physiological lifeline throughout the period of pregnancy. The mature cord is about 1.3 centimetres (0.5 inch) in diameter, and it attains an average length of nearly 50 centimetres (two feet).</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--3--><b><span class="ca" style="color:cadetblue">Formation of the three primary germ layers</b></span></div>
	<div class="m1"><r>I1078.jpg</r> <i></i><r>I1079.jpg</r> <i></i>The inner cell mass, attached to the deep pole of the implanted blastocyst, is sometimes called the embryoblast, since it supplies the materials used in the formation of an embryo. The cellular mass flattens and enters into the process of gastrulation, through which the three primary germ layers segregate and the gastrula stage, the next advance after the blastula, begins to take form. First, cells facing the cavity of the blastocyst arrange into a layer named the endoderm (Figure 1G, H--&#62;<r>I1078.jpg</r>). The thick residual layer, temporarily designated as epiblast, is the source of a definitive uppermost sheet, the  <a class="v2" href="ectoderm">ectoderm</a>, and an intermediate layer, the  <a class="v2" href="mesoderm">mesoderm</a>. In this second phase of gastrulation, some cells of the epiblast migrate to the midline position, then turn downward and emerge beneath as mesoderm. Such cells continue to spread laterally, right and left, between the endoderm and the residue of epiblast, which is now definitive ectoderm (Figure 1Jb--&#62;<r>I1079.jpg</r>).</div>
	<div class="m1">&#160;</div>
	<div class="m1"><r>I1079.jpg</r> <i></i>The site where the migratory mesodermal cells leave the epiblast is an elongated, crowded seam known as the primitive streak (Figure 1Ja--&#62;<r>I1079.jpg</r>). Similar migrating cells produce a thick knob at one end of the primitive streak. Their continued forward movement from this so-called primitive knot produces a dense band that becomes the rodlike notochord.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      The germ layers are not segregated sheets whose cells have predetermined, limited capacities and inflexibly fixed fates in carrying out organ-building activities. Rather, the layers represent advantageously located assembly grounds out of which the component parts of the embryo emerge normally, according to a master constructional plan that assigns different parts to definite spatial positions and local sites. Thus, although the germ layers have developmental potencies in excess of their normal developmental fates, their ordinary participation in organ forming does not deviate from a definite, standard program.</div>
	<div class="m1">&#160;</div>
	<div class="m1">       <a class="v2" href="Derivatives of primary germ layers">Derivatives of primary germ layers</a>The derivatives of the primary germ layers can best be presented in tabular form. In naming the germ-layer origin of an organ, only the principal functional tissue is designated (Table 1 (<a class="v2" href="Derivatives of primary germ layers">Derivatives of primary germ layers</a>)). In a few instances, such as the suprarenal (adrenal) glands and the teeth, a compound organ has important parts of different origin.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--3--><b><span class="ca" style="color:cadetblue">Growth and differentiation</b></span></div>
	<div class="m1"><!--4--><b>Growth</b></div>
	<div class="m1">      Growth is an increase in size, or bulk. Cell multiplication is fundamental to an increase in bulk but does not, by itself, result in growth. It merely produces more units to participate in subsequent growing. Growth is accomplished in several ways. Most important is synthesis, by which new living matter, protoplasm, is created from available foodstuffs. Another method utilizes water uptake; a human embryo of the early weeks is nearly 98 percent water, while an adult is 70 percent fluid. A third method of growth is by intercellular deposition; cells manufacture and extrude such nonliving substances as jelly, fibres, and the ground substance of cartilage and bone. Through these activities a newborn baby is several thousand million times heavier than the zygote from which it came.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      It is obvious that uniform growth throughout the substance of a developing organism would merely produce a steadily enlarging spherical cellular mass. Local diversities in form and proportions result from differential rates of growth that operate in different regions and at different times. The particular program of starting times and growth rates, both externally and internally in the human embryo, constitutes its characteristic growth pattern. Abnormal growth occurs occasionally, and growth may be excessive or deficient. Also, such departures may be general or local, symmetrical or asymmetrical. General  <a class="v2" href="gigantism">gigantism</a> usually starts before birth, and the oversized baby continues to grow at an accelerated rate. (In some instances, the existing hereditary predisposition for gigantism may not be aroused into action until some time during childhood.) In a reverse manner, general  <a class="v2" href="dwarfism">dwarfism</a> may exist before birth, with the individual continuing to grow only a small amount after birth and with growth then stopping at the usual time. In another departure from the usual growth pattern, the individual may be average in size at birth and grow normally for a while, with growth then coming to a premature arrest.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--4--><b>Differentiation</b></div>
	<div class="m1">      In a developing organism, differentiation implies increasing structural and chemical complexity. One kind of differentiation concerns changes in gross shape and organization. Such activities, related to molding the body and its integral parts into form and pattern, comprise the processes called  <a class="v2" href="morphogenesis">morphogenesis</a>. The processes of morphogenesis are relatively simple mechanical acts: (1) cell migration; (2) cell aggregation, forming masses, cords, and sheets; (3) localized growth or retardation, resulting, in enlargements or constrictions; (4) fusion; (5) splitting, including separation of single sheets into separate layers, formation of cavities in cell masses, and forking of cords; (6) folding, including circumscribed folds that produce inpocketings and outpocketings; (7) bending, which, like folding, results from unequal growth.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      A second kind of differentiation refers to progressive changes occurring in the substance and structure of cells, whereby different kinds of tissues are created. These changes, and the synthetic processes underlying them, constitute  <a class="v2" href="histogenesis">histogenesis</a>. The zygote contains all the essential factors for development, but they exist solely as an encoded set of instructions localized in the genes of chromosomes and bearing no direct physical relationship to the future characteristics of the developing embryo. During histogenesis these instructional blueprints are decoded and transformed, through cytoplasmic syntheses, into the several types and subtypes of tissues that are the structural and functional units of organs. At first, the cells of each germ layer lack an identifiable shape and are similar in chemical composition, but an invisible type of chemical differentiation soon enters. After the elaboration of specific enzyme patterns and syntheses, certain groups of cells progressively assume distinctive characters that permit their fates to be recognized. Such early stages in definite lines of differentiation of cells are often designated by the suffix “-blast” (e.g., myoblast; neuroblast).</div>
	<div class="m1">&#160;</div>
	<div class="m1">      The emerging cell types are discrete entities, without intermediates; for example, a transitional form between a muscle cell and a nerve cell is never seen. Neither can different, local parts of a cell carry out different types of tissue specialization, such as nerve at one end and muscle at the other end; nor can a cell, once fully committed to a particular type of specialization, abandon it and adopt a new course.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      Under certain conditions, differentiated cells may, however, return to a simpler state. Thus, under a changed environment, cartilage may lose its matrix, and its cells may come to resemble the more primitive tissue from which it arose. Nevertheless, despite such reversal and apparent simplification (“dedifferentiation”), these cells retain their former histological specificity. Under suitable environmental conditions they can differentiate again but can only regain their previous definitive characteristics as cartilage cells.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      The final result of histogenesis is the production of groups of cells similar in structure and function. Each specialized group constitutes a fundamental tissue. There are four main types of such tissues: each of the three germ layers gives rise to sheetlike epithelia, which cover surfaces, line cavities, and are frequently glandular; ectoderm also forms the nervous tissues; and mesoderm also produces the muscular tissues and it differentiates into blood and the fibrous connective tissues (including two further specialized types, cartilage and bone).</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--3--><b><span class="ca" style="color:cadetblue">Embryonic (<a class="v2" href="embryo">embryo</a>) acquisition of external form</b></span></div>
	<div class="m1"><!--4--><b>Development between the second and fourth weeks</b></div>
	<div class="m1"><r>I1078.jpg</r> <i></i>At the end of the second week, the embryonic region is a nearly circular plate within its well-embedded, differentiating chorionic sac (Figure 1H--&#62;<r>I1078.jpg</r>). This embryonic disk consists of two layers (epiblast and endoderm), with a third layer (mesoderm) just starting to spread between them. A hollow, dome-shaped amnion sac attaches to the margin of the upper layer of the disk, and a hollow yolk sac is similarly continuous with the lower layer. A broad cellular bridge attaches the complex to the chorion.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><r>I1079.jpg</r> <i></i>Early in the third week the embryonic disk has enlarged and become pear-shaped in outline, and a well-formed primitive streak occupies the midline of its caudal (hind) half, which is narrower. Cells from the epiblast are passing through the streak and spreading laterally in both directions beneath the uppermost layer, now ectoderm (Figure 1Ja, Jb--&#62;<r>I1079.jpg</r>). In this way the embryonic disk becomes three layered, and the gastrula stage of development comes to an end. At the middle of the week a thickening, the head process, is extending forward from a knoblike primitive knot located at the head end of the primitive streak. These linear thickenings define the median plane of the future embryo and thus divide the embryonic disk into precise right and left halves.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><r>I1080.jpg</r> <i></i>Toward the end of the week the disk elongates and becomes slipper-shaped in outline; a slight constriction demarcates it from the attached yolk sac. Growth has lengthened the region ahead of the now receding primitive streak. Here, in the midline, the ectoderm bears a definite gutter-like formation called the neural groove; it is the first indication of the future central nervous system. Beneath the groove the mesodermal head process presently rounds into an axial rod, the notochord, that serves as a temporary “backbone.” By the end of the third week a head fold, paired lateral body folds, and a tail fold become prominent, demarcating a somewhat cylindrical embryo from the still broadly attached yolk sac (Figure 1L--&#62;<r>I1080.jpg</r>). The neural folds, flanking the neural groove, converge and begin to meet midway of their lengths, thereby producing a neural tube at that level. Mesoderm, alongside the notochord, begins to subdivide into paired blocks called somites (<a class="v2" href="somite">somite</a>), and the outlines of the somites show externally. From them, muscles and vertebrae will differentiate later. This stage, when the embryo is fashioning a neural tube, is often designated as a neurula.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      In the fourth week the embryo goes beyond the external characteristics of vertebrates in general and becomes recognizable as a mammal. The week is marked by profound changes, during which the embryo acquires its general body plan. There is an increase in total length from about two to five millimetres, but size is quite variable among smaller specimens. Better correlated with the degree of development is the number of mesodermal somites, which attain their full number of about 42 during the fourth week. Some of the head of an early embryo arises from the embryonic disk in front of the primitive knot. But as the primitive streak shortens and its caudal retreat continues, such structures as the neural tube and notochord are added progressively in the wake of that retreat, and additional somite pairs also appear in steady succession.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><r>I1080.jpg</r> <i></i>The most important manoeuvre in the establishment of general body form is the transformation of the flat embryonic disk into a roughly cylindrical early embryo, which is attached to the yolk sac by a slender yolk stalk (Figure 1M--&#62;<r>I1080.jpg</r>). Three factors cooperate in producing this change: (1) There is more rapid expansion of both the embryonic area and the yolk sac than in the region joining the two. The enlarging embryonic area at first buckles upward and then overlaps the more slowly growing margin. Since growth is particularly rapid at the future head end and tail end, the embryo becomes elongate. (2) In conjunction with this overgrowth there is important underfolding, again most pronounced at the front and hind ends. Underfolding is produced by differential elongation in the regions of the brain and tail bud. Conspicuous is the change in the future cardiac (heart) and foregut region, which swings beneath the brain as on a hinge. (3) A certain amount of true constriction, through growth, purses all of these parts at the site of the future umbilicus.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      Throughout the entire period when the body and its parts are being laid down, developmental advances tend to appear first at the head end and then progress tailward. For this reason, many structures that extend along the body for a distance show a gradation in development. The size advantage gained initially by the head end of the embryo is relinquished very slowly. Even in an infant the relatively large head and long arms are striking. A further tendency toward progressively graded development occurs from the middorsal line in a lateral (sideward) and then ventral (frontward) direction. All such relations are the visible expressions of stages in growth and differentiation.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><r>I1080.jpg</r> <i></i>Early in the fourth week the cylindrical shape of the embryo is plain, even though the folding-off process is far from complete (Figure 1M--&#62;<r>I1080.jpg</r>). The neural tube is still open near both ends, and at the head end the broader neural folds indicate the future brain and even its three primary divisions. A pronounced bulge beneath the brain region denotes where the  <a class="v2" href="heart">heart</a> is forming precociously in order to institute a necessary, prompt circulation through the placenta.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      During the middle and late days of the fourth week there are marked advances. Accelerated growth along the dorsal region bends the total body length progressively until the embryo assumes a striking C-shape, with the tips of the head and  <a class="v2" href="tail">tail</a> not far apart. Continued growth and underfolding close in much of the ventral side of the embryo, so that a free head and upper trunk, and a lower trunk and a prominent tail, are easily recognizable. Forebrain, midbrain, and hindbrain can be identified, largely because of a sharp bend in the midbrain. Local outgrowths from each side of the forebrain produce stalked eye cups, and a pair of inpocketings of the ectoderm alongside the hindbrain sink beneath the surface as otic vesicles, forerunners of the inner ears (<a class="v2" href="inner ear">inner ear</a>). Bulges indicative of the heart and liver are prominent. Formations called branchial arches, reminiscent of the gill arches of fishes and aquatic amphibians, become conspicuous in the future jaw–neck region. Paired swellings (“buds”) off the trunk foretell the locations of the upper and lower limbs.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--4--><b>Developmental changes in the fifth to eighth weeks</b></div>
	<div class="m1">      A five-week embryo is about eight millimetres (0.32 inch) long, whereas at six weeks the length is about 13 millimetres (0.52 inch). New external features are olfactory pits at the tips of the bent head. An umbilical cord becomes a definite entity, its proximal end occupying a low position on the abdominal wall. The sharply bent head joins the rest of the body at an acute angle. The first pair of branchial arches branch Y-fashion into maxillary and mandibular processes (primitive upper and lower jaws). The external ears are forming around the paired grooves located between each half of the mandible and each second branchial arch. The heart, which was previously the chief ventral prominence, now shares this distinction with the rapidly growing liver. Limb buds have elongated markedly and become flattened at their outer ends. A constriction on each bud separates a paddle-like hand plate or foot plate from a cylindrical segment attached to the body wall. Predictably, the upper limbs are somewhat further advanced than the lower pair.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      In the latter weeks of the second month, developmental changes advance from those that distinguish primates to a state that is recognizably human. At the end of the second month (30 millimetres', or 1.2 inches', length) the stage of the embryo ends; henceforth, until birth, “fetus” (<a class="v2" href="fetus">fetus</a>) is the preferred term.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      In the seventh and eighth weeks the head becomes more erect and the previously curved trunk becomes straighter. The heart and liver, which earlier dominated the shape of the ventral body, yield to a more evenly rounded chest–abdomen region. The tail, which at an earlier time was one fifth of the embryo's length, becomes inconspicuous both through actual regression and through concealment by the growing buttocks. The  <a class="v2" href="face">face</a> rapidly acquires a fairly human appearance; eyes, ears, and jaws are prominent. The eyes, previously located on the sides of the head, become directed forward; the  <a class="v2" href="nose">nose</a> lacks a bridge and so is of the “pug” type, with the nostrils directed forward instead of downward. A mandibular branch of each Y-shaped branchial arch combines with its mate to form the lower  <a class="v2" href="jaw">jaw</a>. The maxillary branch on each side joins an elevation on the medial (inner) side of the corresponding nostril to produce the more complicated upper jaw. Branchial arches, other than those forming the jaws and external ears, are effaced through incorporation into an emerging, recognizable neck. Limbs become jointed, and the earlier hand plates and  <a class="v2" href="foot">foot</a> plates differentiate terminal digits. Primitive external genitalia appear, but in a nondistinctive, sexless condition.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      Almost all of the internal organs are well laid down at the end of eight weeks, when the embryo is little more than 25 millimetres (one inch) long. The characteristic external features are established, and subsequent growth merely modifies existing proportions without adding new structure. Similarly, the chief changes undergone by internal organs and parts are those of growth and tissue specialization. At eight weeks the neuromuscular mechanism attains a degree of perfection that permits some response to delicate stimulation.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--4--><b>Development from the third month to birth</b></div>
	<div class="m1"><r>I1081.jpg</r> <i></i>During the third month the young fetus clearly resembles a human being, although the head is disproportionately large (Figure 1P--&#62;<r>I1081.jpg</r>). The previous protrusion of much of the intestine into the umbilical cord is reduced through the return of its loops into the abdomen. The ears rise to eye level and the eyelids (<a class="v2" href="eyelid">eyelid</a>) fuse shut. Nails begin forming; ossification (bone-forming) centres appear in most of the future bones; and the sex of external genitalia becomes recognizable. (In this paragraph, and in the next two, the months are lunar months, of 28 days).</div>
	<div class="m1">&#160;</div>
	<div class="m1">      At four months individual differences between the faces of fetuses become distinguishable. The face is broad but the eyes are now less widely separated. The umbilical cord attaches higher on the abdominal wall; this location is above an expanding region between the cord and the pubis (front bones of the pelvis) that scarcely existed previously.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      At five months downy hairs (lanugo) cover the body, and some head hairs appear. The skin is less transparent. Fetal movements (“quickening”) are felt by the mother. At six months eyebrows and eyelashes are clearly present. The body is lean, but its proportions have improved. The  <a class="v2" href="skin">skin</a> is wrinkled. At seven months the fetus resembles a dried-up old person. Its reddish, wrinkled skin is smeared with a greasy substance (vernix caseosa). The eyelids reopen. At eight months fat is depositing beneath the skin. The testes begin to invade the scrotum. At nine months the dull redness of the skin fades and wrinkles smooth out. The body and limbs become better rounded.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      At full term (38 weeks) the body is plump and proportions are improved, although the head is large and the lower limbs are still slightly shorter than the upper limbs. The skin has lost its coat of lanugo hair, but it is still smeared with vernix caseosa. Nails project beyond the finger tips and to the tips of toes. The umbilical cord now attaches to the centre of the abdomen. The testes of males are usually in the scrotum; the greater lips of the female external genitalia, which previously gaped, are now in contact. Cranial bones meet except at some angular junctions, or “soft spots.”</div>
	<div class="m1">&#160;</div>
	<div class="m1">       <a class="v2" href="Age, size, and weight">Age, size, and weight</a>The average time of delivery (<a class="v2" href="parturition">parturition</a>) is 280 days from the beginning of the last menstrual period, whereas the duration of  <a class="v2" href="pregnancy">pregnancy</a> (age of the baby) is about 266 days (38 weeks; Table 2 (<a class="v2" href="Age, size, and weight">Age, size, and weight</a>)). Pregnancy may extend to 300 days, or even more, in which case the baby tends to be heavier. Even when treated in a neonatal unit, premature babies born under 27 weeks of age are less likely to survive, whereas those more than 30 weeks old usually do survive.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--2--><i><b><span class="ca" style="color:darkcyan">Abnormal development</b></i></span></div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--3--><b><span class="ca" style="color:cadetblue">Multiple births</b></span></div>
	<div class="m1">      It is both unusual and abnormal for the human species to produce more than one offspring at a time. “Twins” (<a class="v2" href="twin">twin</a>) and “twinning” are used as general terms for any number of multiple births, as the same basic principles apply.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      Fraternal twins stem from multiple ovulations in the same cycle. Each oocyte develops singly in a separate follicle, is shed and fertilized individually, develops within its own chorionic sac, and forms an individual placenta. In some instances, two blastocysts implant close together and the expanding placentas meet and fuse. In such double placentas, however, the two blood circulations rarely communicate. The word dizygotic technically designates two-egg twins. Such pairs obviously are independent in sex determination and bear no more resemblance than do other children of the same parents. Properly speaking, they are merely littermates. Nearly three-fourths of all American twins are dizygotic, whereas the Japanese ratio is only one-fourth. A tendency toward such multiple births exists in some family lines.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      Wholly different are those true twins who are always of the same sex and are strikingly similar in physical, functional, and mental traits. Such close identity is enforced by their derivation from a single ovulated and fertilized egg, and hence by their acquisition of identical chromosomal constitutions. This twin type is named monozygotic. Three-fourths of such pairs develop within a common chorionic sac and share a placenta; one-fourth have individual sacs and placentas. The latter condition results from a mishap before implantation, when the cleavage cells separate into two groups and then become individually implanting blastocysts. There is no discernible hereditary tendency toward the production of monozygotic twins.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      Several atypical processes involving the inner cell mass or embryonic plate can produce separate embryos within a single sac: (1) The inner cells of a blastocyst may segregate into two masses. (2) Somewhat later in time, two embryonic axes may become established on a single embryonic disk. (3) A single axis may subdivide by fission or budding. (4) Duplication of any sort may combine with secondary subdivision; the Dionne quintuplets are believed to have followed this sequence, which is also normal for the regular quadruplets of the Texas armadillo.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      Occasionally monozygotic twinning can result in fused, or conjoined, twins. Conjoining results from divergent growth at the front or hind end of the emerging primitive axis of an embryo, or at both ends. The degree of union varies from slight to extensive, and the possession of a single or double set of internal organs depends on the intimacy of fusion at any particular level. Union occurs by the heads, upper trunks, or lower trunks; the joining may be by the dorsal, lateral, or ventral surfaces. Sometimes there is a marked disparity in the size of the two twins; this condition is known as nonsymmetric twinning, and usually the much smaller twin will be dependent on the larger for nutrition.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--3--><b><span class="ca" style="color:cadetblue">Fetal deviations (<a class="v2" href="congenital disorder">congenital disorder</a>)</b></span></div>
	<div class="m1">      Human embryos are subject to disease, abnormal development, and abnormal growth. Decline and death can occur at any stage, but since most deaths occur in the first two or three weeks of development they usually escape notice. Probably little more than half of all zygotes reach full-term birth. Most abnormalities resulting from faulty development originate within the first seven weeks of pregnancy, before the prospective mother is aware of her condition. Abnormalities that do occur in living infants tend toward the milder types, since the severe mishaps commonly terminate development before birth.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      Folklore (<a class="v2" href="folk literature">folk literature</a>) maintains that a pregnant woman may “mark” her baby through incurring physical injuries or becoming subjected to horrors or repulsive sights. As there are no nervous connections between mother and fetus, such beliefs lack foundation. Moreover, practically all of the alleged causative experiences occur long after the “related” abnormalities have been established in the embryo.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      Defective health of the mother can, in some instances, become a cause of the physical impairment or death of a fetus. Certain infectious diseases (<a class="v2" href="infectious disease">infectious disease</a>), for example, may result in fetal injury; such related causative organisms can be a virus (German measles), a spirochetal microorganism ( <a class="v2" href="syphilis">syphilis</a>), or a protozoon parasite ( <a class="v2" href="toxoplasmosis">toxoplasmosis</a>). Also, placental disorders, malformations of the mother's reproductive organs, and inadequate functioning of her endocrines may provide an unfavourable environment for normal development. Birth itself imposes the risk of oxygen deficiency or other injury; either may result in some malfunctioning of the brain.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--3--><b><span class="ca" style="color:cadetblue"> <a class="v2" href="teratology">teratology</a></b></span></div>
	<div class="m1">       <a class="v2" href="teratology">teratology</a> is concerned with all features of abnormal generation and development of the embryo (embryogenesis) and their end products. The incidence of defective development is high. One infant in 14 that survive the neonatal period bears an abnormality of some kind and degree, and half of these babies have more than one malformation. Internal, concealed defects are more numerous than external ones, and some defects do not become apparent until childhood. One baby in 40 is born with a structural defect that needs treatment. Some types of abnormality are commoner in males (e.g., pyloric stenosis, the narrowing of the opening between the stomach and the intestine), while other types predominate in females (e.g., dislocated hip). Besides obvious congenital defects, there are aberrations at the molecular level known as inborn errors of metabolism. In these an enzyme deficiency blocks the course of intermediary metabolism and results in abnormal chemical functioning. Such errors involve proteins, carbohydrates, lipids, and pigments. The abnormal products may be stored or excreted.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      Important among causes of abnormalities are hereditary (<a class="v2" href="heredity">heredity</a>) factors. Such include gene mutations (<a class="v2" href="mutation">mutation</a>), which may become Mendelian dominants (e.g., fused fingers, which need be inherited from only one parent to appear in the offspring), recessives (e.g., albinism, which does not become evident unless its gene is inherited from both parents), or sex-linked factors (e.g., hemophilia). Besides the heritable defects, whose possibilities of recurrence can be estimated, there are many genetic results that are due to chance, are not passed on, and do not occur in other offspring. An unequal distribution of chromosomes during meiosis, leading to abnormal assortments, occurs in somatic (nonsex) chromosomes (e.g., Down's syndrome (<a class="v2" href="Down syndrome">Down syndrome</a>)) and in sex chromosomes (e.g.,  <a class="v2" href="Klinefelter's syndrome">Klinefelter's syndrome</a>).</div>
	<div class="m1">&#160;</div>
	<div class="m1">      Environmental factors, both external and internal, are also important. Among physical agents, mechanical pressures or blows are no longer considered to be significant because of the protection supplied by the uterus and the fluid-filled amniotic sac. On the other hand, irradiation is a wholly effective physical agency, as experiments have amply proved. Various chemical agents, used experimentally on pregnant animals, are highly teratogenetic (productive of physical defects within the uterus), and at least one drug (thalidomide) has demonstrated its potency on human embryos. Deficiencies of some fetal hormones (<a class="v2" href="hormone">hormone</a>) are associated causally with bodily defects (e.g., male hormone and false  <a class="v2" href="hermaphroditism">hermaphroditism</a>, a condition in which the gonads are of one sex but some appearances suggest the other). Similarly, hormonal excess can be productive (e.g., growth-promoting hormone and gigantism).</div>
	<div class="m1">&#160;</div>
	<div class="m1">      There appear to be several ways in which teratogenetic agents can affect susceptible embryonic cells. But whatever the manner of interference may be, the final result is probably either cell impairment or death or a changed rate of growth. Either of these measures puts local development out of step with adjoining parts and upsets the coordinated schedule of development.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--2--><i><b><span class="ca" style="color:darkcyan">Development of organs</b></i></span></div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--3--><b><span class="ca" style="color:cadetblue">Ectodermal (<a class="v2" href="ectoderm">ectoderm</a>) derivatives</b></span></div>
	<div class="m1"><!--4--><b>Integumentary system</b></div>
	<div class="m1">      The skin has a double origin. Its superficial layer, or  <a class="v2" href="epidermis">epidermis</a>, develops from ectoderm. The initial single-layered sheet of epithelial cells becomes multilayered by proliferation, and cells nearer the surface differentiate a horny substance. Pigment granules appear in the basal layer, at least, of all races. The epidermis of the palm and sole becomes thicker and more specialized than elsewhere. Cast-off superficial cells and downy hairs mingle with a greasy glandular secretion and smear the skin in the late fetal months; the pasty mass is called vernix caseosa. The deep layer of the skin, or  <a class="v2" href="dermis">dermis</a>, is a fibrous anchoring bed differentiated from mesoderm. In the later fetal months the plane of union between epidermis and dermis becomes wavy. The permanently ridged patterns are notable at the surface of the palm and sole.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      Nails (<a class="v2" href="nail">nail</a>) develop in pocket-like folds of the skin near the tips of digits. During the fifth month specialized horny material differentiates in proliferating ectodermal cells. The resulting nail plate is pushed forward as new plate substance is added in the fold. Fingernails reach the finger tips one month before birth. Hairs, produced only by mammals, begin forming in the third month as cylindrical pegs that grow downward from the epidermis into the dermis. Cells at the base of the peg proliferate and produce a horny, pigmented thread that moves progressively upward in the axis of the original cylinder. This first crop of hairs is a downy coat named lanugo. It is prominent by the fifth month but is mostly cast off before birth. Unlike nails, hairs are shed and replaced periodically throughout life.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      Sebaceous glands (<a class="v2" href="sebaceous gland">sebaceous gland</a>) develop into tiny bags, each growing out from the epithelial sheath that surrounds a hair. Their cells proliferate, disintegrate, and release an oily secretion. Sweat glands at first resemble hair pegs, but the deep end of each soon coils. In the seventh month an axial cavity appears and later is continued through the epidermis. The mammary glands (<a class="v2" href="mammary gland">mammary gland</a>), peculiar to mammals, are specialized sweat glands. In the sixth week a thickened band of ectoderm extends between the bases of the upper and lower limb buds. In the pectoral (chest) region only, gland buds grow rootlike into the primitive connective tissue beneath. During the fifth month 15 to 20 solid cords foretell the future ducts of each gland. Until late childhood the mammary glands are identical in both sexes.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--4--><b> <a class="v2" href="mouth">mouth</a> and anus</b></div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--5--><b>Mouth</b></div>
	<div class="m1">      The mouth is a derivative of the stomodaeum, an external pit bounded by the overjutting primitive nasal region and the early upper and lower jaw projections. Its floor is a thin membrane, where ectoderm and endoderm fuse. Midway in the fourth week this membrane ruptures, making continuous the primitive ectodermal mouth and endodermal pharynx (throat). Lips and cheeks arise when ectodermal bands grow into the mesoderm and then split into two sheets. Teeth (<a class="v2" href="tooth">tooth</a>) have a compound origin: the cap of enamel develops from ectoderm, whereas the main mass of the tooth, the dentin, and the encrusting cementum about the root differentiate from mesoderm. The salivary glands (<a class="v2" href="salivary gland">salivary gland</a>) arise as ectodermal buds that branch, bushlike, into the deeper mesoderm. Berrylike endings become the secretory acini (small sacs), while the rest of the canalized system serves as ducts. The palate is described in relation to the nasal passages. A tiny pocket detaches from the ectodermal roof of the stomodaeum and becomes the anterior, or frontward, lobe of the hypophysis, also called the pituitary. The anterior lobe fuses with the neural lobe of the gland (see below).</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--5--><b> <a class="v2" href="anus">anus</a></b></div>
	<div class="m1">      A double-layered, oval membrane separates the endodermal hindgut from an ectodermal pit, called the proctodaeum, the site of the future anal canal and its orifice, the anus. Rupture at eight weeks creates a communication between the definitive anus and the rectum.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--4--><b>Central nervous system (<a class="v2" href="nervous system, human">nervous system, human</a>)</b></div>
	<div class="m1"><r>I1080.jpg</r> <i></i>Both the brain and the spinal cord arise from an elongate thickening of the ectoderm that occupies the midline region of the embryonic disk. The sides of this neural plate elevate as neural folds, which then bound a gutter-like neural groove (Figure 1L--&#62;<r>I1080.jpg</r>). Further growth causes the folds to meet and fuse, thereby creating a neural tube. The many-layered wall of this tube differentiates into three concentric zones, first indicated in embryos of five weeks. The innermost zone, bordering the central canal, becomes a layer composed of long cells called ependymal cells, which are supportive in function. The middle zone becomes the gray substance, a layer characterized by nerve cells. The outermost zone becomes the white substance, a layer packed with nerve fibres. The neural tube also is demarcated internally by a pair of longitudinal grooves into dorsal and ventral halves. The dorsal half is a region associated with sensory functioning and the ventral half with motor functioning.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      The gray substance contains primitive stem cells, many of which differentiate into neuroblasts. Each neuroblast becomes a  <a class="v2" href="neuron">neuron</a>, or a mature nerve cell, with numerous short branching processes, the dendrons, and with a single long process, the axon. The white substance lacks neuroblasts but contains closely packed axons, many with fatty sheaths that produce the whitish appearance. The primitive stem cells of the neural tube also give rise to nonnervous cells called neuroglia cells.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--5--><b> <a class="v2" href="brain">brain</a></b></div>
	<div class="m1">      The head end of the neural plate becomes expansive even as it closes into a tube. This brain region continues to surpass the spinal cord region in size. Three enlargements are prominent, the forebrain, midbrain, and hindbrain. The forebrain gives rise to two secondary expansions, the telencephalon and the diencephalon. The midbrain, which remains single, is called the mesencephalon. The hindbrain produces two secondary expansions called the metencephalon and the myelencephalon.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      The telencephalon outpouches, right and left, into paired cerebral hemispheres, which overgrow and conceal much of the remainder of the brain before birth. Late in fetal life the surface of the  <a class="v2" href="cerebrum">cerebrum</a> becomes covered with folds separated by deep grooves. The superficial gray cortex is acquired by the migration of immature nerve cells, or neuroblasts, from their primary intermediate position in the neural wall. The diencephalon is preponderantly gray substance, but its roof buds off the pineal body, which is not nervous tissue, and its floor sprouts the stalk and neural (posterior) lobe of the pituitary. The mesencephalon largely retains its early tubular shape. The metencephalon develops dorsally into the imposing  <a class="v2" href="cerebellum">cerebellum</a>, with hemispheres that secondarily gain convolutions clothed with a gray cortex. The myelencephalon is transitional into the simpler spinal cord. Roof regions of the telencephalon, diencephalon, and myelencephalon differentiate the vascular choroid plexuses (including portions of the pia mater, or innermost brain covering, that project into the ventricles, or cavities, of the brain). The choroid plexuses secrete cerebrospinal fluid.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--5--><b> <a class="v2" href="spinal cord">spinal cord</a></b></div>
	<div class="m1">      For a time the spinal cord portion of the neural tube tapers gradually to an ending at the tip of the spine. In the fourth month it thickens at levels where nerve plexuses, or networks, supply the upper and lower limbs; these are called the cervical and lumbosacral enlargements. At this time the spine begins to elongate faster than the spinal cord. As a result, the caudal (hind) end of the anchored cord becomes progressively stretched into a slender, nonnervous strand known as the terminal filament. Midway in the seventh month the functional spinal cord ends at a level corresponding to the midpoint of the kidneys. Both the brain and the spinal cord are covered with a fibrous covering, the dura mater, and a vascular membrane, the pia-arachnoid. These coverings differentiate from local, neighbouring mesoderm.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--4--><b>Peripheral nervous system</b></div>
	<div class="m1">      In general, each craniospinal nerve has a dorsal (posterior) root that bears a  <a class="v2" href="ganglion">ganglion</a> (mass of nerve tissue) containing sensory nerve cells and their fibres, and a ventral (anterior) root that contains motor nerve fibres but no nerve cells. Ganglion cells differentiate from cells of the neural crest, which is at first a cellular band pinched off from the region where each neural fold continues into ordinary ectoderm. Each of these paired bands breaks up into a series of lumps, spaced in agreement with the segmentally arranged mesodermal somites. Neuroblasts within these primordial ganglia develop a single stem and hence are called unipolar. From this common stem one nerve process, or projection, grows back into the adjacent sensory half of the neural tube; another projection grows in the opposite direction, helping to complete the dorsal root of a nerve. Neuroblasts of motor neurons arise in the ventral half of the gray substance of the neural tube. They sprout numerous short, freely branching projections, the dendrons, and one long, little-branching projection, the axon; (<a class="v2" href="axon">axon</a>) such a neuron is called multipolar. These motor fibres grow out of the neural tube and constitute a ventral root. As early as the fifth week they are joined by sensory fibres of the dorsal root and continue as a nerve trunk.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      Cells of the  <a class="v2" href="neural crest">neural crest</a> differentiate into things other than sensory neurons. Among these variants are cells that encapsulate ganglion cells and others that become neurolemma cells, which follow the peripherally growing nerve fibres and ensheath them. The neurolemma cells cover some nerve fibres with a fatty substance called myelin.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--5--><b>Spinal nerves (<a class="v2" href="spinal nerve">spinal nerve</a>)</b></div>
	<div class="m1">      Spinal nerves are sensorimotor nerves, with dorsal and ventral roots. A network called a brachial plexus arises in relation to each upper limb and a lumbosacral plexus in relation to each lower limb. The spine, elongating faster than the spinal cord, drags nerve roots downward, since each nerve must continue to emerge between the same two vertebrae. Because of their appearance, the obliquely coursing nerve roots are named the cauda equina, the Latin term for horse's tail.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--5--><b>Cranial nerves (<a class="v2" href="cranial nerve">cranial nerve</a>)</b></div>
	<div class="m1">      Cranial nerves V, VII, IX, and X arise in relation to embryonic branchial arches but have origins similar to the spinal nerves. The olfactory nerves (cranial nerve I) are unique in that their cell bodies lie in the olfactory epithelium (the surface membrane lining the upper parts of the nasal passages), each sending a nerve fibre back to the brain. The so-called optic nerves (<a class="v2" href="optic nerve">optic nerve</a>) (II) are not true nerves but only tracts that connect the retina (a dislocated portion of the brain) with the brain proper. Nerves III, IV, VI, and XII are pure motor nerves that correspond to the ventral roots of spinal nerves. The acoustic nerves (<a class="v2" href="vestibulocochlear nerve">vestibulocochlear nerve</a>) (VIII) are pure sensory nerves, each with a ganglion that subdivides for auditory functions and functions having to do with equilibrium and posture; they correspond to dorsal roots. Nerves X and XI are a composite of which XI is a motor component.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--4--><b> <a class="v2" href="autonomic nervous system">autonomic nervous system</a></b></div>
	<div class="m1">      The autonomic nervous system is made up of two divisions, the sympathetic and the parasympathetic nervous systems; it controls such involuntary actions as constriction of blood vessels. Some cells of the neural crests migrate and form paired segmental masses alongside the aorta, a principal blood vessel. Part of the cells become efferent multipolar ganglion cells (cells whose fibres carry impulses outward from ganglions, or aggregates of nerve cells) and others merely encapsulate the ganglion cells. These autonomic ganglia link into longitudinal sympathetic trunks. Some of the neuroblasts migrate farther and assemble as collateral ganglia—ganglia not linked into longitudinal trunks. Still others migrate near, or within, the visceral organs that they will innervate and produce terminal ganglia. These ganglia are characteristic of the parasympathetic system.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      Some cells of certain primitive collateral ganglia leave and invade the amassing mesodermal cortex of each adrenal gland. Consolidating in the centre, they become the endocrine cells of the medulla.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--4--><b>Sense (<a class="v2" href="sensory reception, human">sensory reception, human</a>) organs</b></div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--5--><b>Olfactory organ (<a class="v2" href="smell">smell</a>)</b></div>
	<div class="m1">      Paired thickenings of ectoderm near the tip of the head infold and produce olfactory pits. These expand into sacs in which only a relatively small area becomes olfactory in function. Some epithelial cells in these regions remain as inert supporting elements. Others become spindle-shaped olfactory cells. One end of each olfactory cell projects receptive olfactory hairs beyond the free surface of the epithelium. From the other end a nerve fibre grows back and makes a connection within the brain.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--5--><b>Gustatory organ</b></div>
	<div class="m1">      Most  <a class="v2" href="taste">taste</a> buds arise on the  <a class="v2" href="tongue">tongue</a>. Each bud, a barrel-shaped specialization within the epithelium that clothes certain lingual papillae (small projections on the tongue), is a cluster of tall cells, some of which have differentiated into taste cells whose free ends bear receptive gustatory hairs. Sensory nerve fibres end at the surface of such cells. Other tall cells are presumably inertly supportive in function.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--5--><b>Eye (<a class="v2" href="eyeball">eyeball</a>)</b></div>
	<div class="m1">      The earliest indication of an eye is an optic vesicle (sac) bulging from each side of the forebrain. It quickly becomes an indented optic cup, connected to the brain by a slender optic stalk. Most of the cup will become the  <a class="v2" href="retina">retina</a>, but its rim represents the epithelial part of the insensitive ciliary body and iris. The thicker inner layer of the cup becomes the neural layer of the retina, and by the sixth month three strata of neurons are recognizable in it: (1) visual cells, each bearing either a photoreceptive rod or a cone at one end; (2) bipolar cells, intermediate in position; (3) ganglion cells, which sprout axons that grow back through the optic stalk and make connections within the brain. The thin outer layer of the cup remains a simple epithelium whose cells gain pigment and make up the pigment epithelium of the retina.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      The lens arises as a thickening of the ectoderm adjacent to the optic cup. It inpockets to form a lens vesicle and then detaches. The cells of its back wall become tall, transparent lens fibres. Mesoderm surrounding the optic cup specializes into two accessory coats. The outer coat, the tough, white sclera, is continuous with the transparent cornea. The inner coat, the vascular choroid, continues as the vascular and muscular ciliary body and the vascularized tissue of the iris. The eyelids are folds of adjacent skin; from the inside of each upper lid several lacrimal glands bud out.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--5--><b>Ear (<a class="v2" href="hearing">hearing</a>)</b></div>
	<div class="m1">      The projecting part (auricle) of the external ear develops from hillocks on the first and second branchial arches. The ectodermal groove between those arches deepens and becomes the external auditory canal. The auditory tube and tympanic cavity—the cavity at the inner side of the eardrum (<a class="v2" href="tympanic membrane">tympanic membrane</a>)—are expansions of the endodermal pouch located between the first and second branchial arches. The area where ectodermal groove and endodermal pouch come in contact is the site of the future eardrum. The chain of three auditory ossicles (<a class="v2" href="ear bone">ear bone</a>) (small bones) that stretches across the tympanic cavity is a derivative of the first and second arches.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      The epithelium of the internal ear is at first a thickening of ectoderm at a level midway of the hindbrain. This plate inpockets and pinches off as a closed sac, the otocyst. Its ventral part elongates and coils to resemble a snail's shell, thereby forming the cochlear duct, or seat of the organ of hearing. A middle region of the otocyst becomes chambers known as the utricle and saccule, related to the sense of balance. The dorsal part of the otocyst remodels drastically into three semicircular ducts, related to the sense of rotation. Fibres of the acoustic nerve grow among specialized receptive cells differentiated in certain regions of these three divisions.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--3--><b><span class="ca" style="color:cadetblue">Mesodermal derivatives</b></span></div>
	<div class="m1"><!--4--><b>Skeletal system (<a class="v2" href="skeletal system, human">skeletal system, human</a>)</b></div>
	<div class="m1">      Except for part of the skull, all bones pass through three stages of development: membranous, cartilaginous, and osseous. The earliest ossification centres appear in the eighth week, but some do not arise until childhood years and even into adolescence.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--5--><b>Axial skeleton</b></div>
	<div class="m1">      The ventromedial walls (the walls toward the front and the midline) of the paired somites break down, and their cells migrate toward the axial notochord and surround it. Differentiation and growth of these segmental masses produce the jointed vertebrae. Ribs (<a class="v2" href="rib">rib</a>) also grow out of each primitive vertebral mass, but they become long only in the thoracic region. Here their ventral ends join the sternum, which arises independently by the fusion of a pair of bars.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      The  <a class="v2" href="skull">skull</a> has three components, different in origin. Its basal region is an ancient heritage whose bones pass through the three typical stages of development. By contrast, the sides and roof of the skull develop directly from membranous primordia, or rudiments. The jaws are derivatives of the first pair of cartilaginous branchial arches but develop as membrane bone. Ventral ends of the second to fifth arches contribute the cartilages of the larynx and the hyoid bone (a bone of horseshoe shape at the base of the tongue). Dorsal ends of the first and second arches become the three auditory ossicles (the small bones in the middle ear).</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--5--><b>Appendicular skeleton</b></div>
	<div class="m1">      The limb bones develop in three stages from axial condensations in the local mesoderm. The shoulder and pelvic supports are comparable sets, as are the bones of the arms and legs.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--5--><b>Articulations</b></div>
	<div class="m1">      Some type of  <a class="v2" href="joint">joint</a> exists wherever bones meet. Joints that allow little or no movement consist of connective tissue, cartilage, or bone. Movable joints arise as fluid-filled clefts in mesoderm, which condenses peripherally into a fibrous capsule.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--4--><b>Muscular system</b></div>
	<div class="m1">      Much of each somite differentiates into myoblasts (primitive muscle (<a class="v2" href="muscle system, human">muscle system, human</a>) cells) that become voluntary muscle fibres. Aggregations of such fibres become muscles of the neck and trunk. Muscles of the head and some of the neck muscles originate from mesoderm of branchial arches. Muscles of the limbs seemingly arise directly from local mesoderm. In general, muscle primordia may fuse into composites, split into subdivisions, or migrate away from their sites of origin. During these changes they retain their original nerve supply. Regardless of differences in source of origin, all voluntary muscle fibres are of the same striated type (marked by dark and light stripes). Spontaneous movements begin to occur in embryos about 10 weeks old. In general, involuntary muscle differentiates from mesoderm surrounding hollow organs; only the cardiac muscle type is striated.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--4--><b>Vascular system (<a class="v2" href="human cardiovascular system">human cardiovascular system</a>)</b></div>
	<div class="m1">      All hollow organs, including arteries, veins, and lymphatics, are lined with epithelium—the principal functional tissue—and are ensheathed with muscular and fibrous coats.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--5--><b>Blood vessels (<a class="v2" href="blood vessel">blood vessel</a>)</b></div>
	<div class="m1">      Primitive blood vessels arise in the mesoderm as tiny clefts bordered by flat endothelial cells. Growth and coalescence produce networks, out of which favoured channels persist as definite vessels, while others decline and disappear. A bilaterally symmetrical system of vessels is well represented in embryos four weeks old. This early plan is profoundly altered and made somewhat asymmetrical during the second month by fusions, atrophies, emergence of new vessels, and rerouting of older ones. The alterations reflect adjustments to changing form and pattern within the developing organ systems.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      Arteries cranial to the heart (headward of the heart) are mostly products of the paired aortic arches, which course axially within the branchial arches, thus interconnecting the ventral  <a class="v2" href="aorta">aorta</a> with paired dorsal aortas. The third pair of aortic arches becomes the common carotids; the fourth pair, the aortic arch and brachiocephalic; the fifth pair, the pulmonary arteries and ductus arteriosus. The dorsal aortas fuse into the single descending aorta, which bears three sets of paired, segmental branches. The dorsal set becomes the subclavian, intercostal, and lumbar arteries. The lateral set becomes arteries to the diaphragm, the adrenal glands, the kidneys, and the sex glands. The ventral set becomes the celiac, mesenteric, and umbilical arteries. Axial arteries to both sets of limb buds emerge from an original plexus, but they undergo drastic alteration and extensive replacement.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      The primitive veins are symmetrically bilateral. They consist of vitelline veins from the yolk sac, umbilical veins from the placenta, and precardinal and postcardinal veins from the cranial and caudal regions (the regions toward the head and toward the tail) of the body. Drastic transformations occur in all of these, and new pairs of veins (subcardinals and supracardinals) arise also, caudal to the heart. From the vitellines come chiefly the portal and hepatic veins. The left umbilical becomes the main return from the placenta by making a diagonal channel, the ductus venosus, through the liver to the heart. The precardinal veins change their names to the internal jugulars, but near the heart an interconnection permits both to drain into a common stem, then called the superior vena cava. Caudal to the heart, the postcardinals virtually disappear, and all blood return shifts to the right side as a new compound vessel, the inferior vena cava, becomes dominant. Pulmonary veins open into the left atrium. Veins from the limb buds organize from an early peripheral border vein.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--5--><b>Lymphatic (<a class="v2" href="lymphatic system">lymphatic system</a>) vessels</b></div>
	<div class="m1">      The lymph vessels develop independently in close association with veins. Linkages produce the thoracic duct, which is the main drainage return for lymph. Masses of lymphocytes accumulate about lymphatic vessels and organize as lymph nodes. The spleen has somewhat similar tissue, but its channels are supplied with blood.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--5--><b> <a class="v2" href="heart">heart</a></b></div>
	<div class="m1"><r>I1080.jpg</r> <i></i>Fusion combines two endothelial tubes, and these are surrounded by a mantle of mesoderm that will become the muscular and fibrous coats of the heart. At three weeks the heart is a straight tube that is beginning to beat (Figure 1M--&#62;<r>I1080.jpg</r>). Starting at the head end, four regions can be recognized: bulbus, ventricle, atrium, and sinus venosus. Since the heart is anchored at both ends, rapid elongation forces it to bend. In doing this, the sinus–atrium and bulbus–ventricle reverse their original relations. Further development concerns the transformation of a single-chambered heart into one with four chambers.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      The  <a class="v2" href="atrium">atrium</a> becomes subdivided by the growth of two incomplete partitions, or septa, placed close together and each covering the defect in the other. The  <a class="v2" href="ventricle">ventricle</a> also subdivides, but by a single, complete partition. A canal, connecting atria and ventricles, becomes two canals. The bulbus is absorbed into the right ventricle, and its continuation (the truncus) subdivides lengthwise, forming the aorta and the pulmonary artery. The right horn of the sinus venosus is absorbed into the right atrium, together with the superior and inferior venae cavae, which originally drained into the sinus. The transverse portion of the sinus persists as the coronary sinus. The pulmonary veins retain their early drainage into the left atrium. Important valves develop and ensure flow within the heart from atria to ventricles, and outward from the ventricles into the aorta and the pulmonary artery.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      Birth initiates breathing, and the abandonment of the placental circulation follows. These changes entail a drastic rerouting of blood through the heart. As a result, the two atrial septa fuse and no longer permit blood to pass from the right atrium to the left atrium. Blood in the pulmonary artery no longer virtually bypasses the lungs; previously it had passed to the aorta directly through a shunt offered by the ductus arteriosus. As a sequel to these changes, the abandoned umbilical arteries, umbilical vein, ductus venosus, and ductus arteriosus all collapse and become fibrous cords.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--4--><b>Urinary system (<a class="v2" href="renal system">renal system</a>)</b></div>
	<div class="m1">      Vertebrates have made three experiments in  <a class="v2" href="kidney">kidney</a> production: the  <a class="v2" href="pronephros">pronephros</a>, or earliest type; the  <a class="v2" href="mesonephros">mesonephros</a>, or intermediate kidney; and the  <a class="v2" href="metanephros">metanephros</a>, or permanent kidney. All arise from the cellular plates called nephrotomes that connect somites with the mesodermal sheets that bound the body cavity. The vestigial pronephros is represented solely by several pairs of tubules; they join separately formed excretory ducts that grow downward and enter the  <a class="v2" href="cloaca">cloaca</a>, the common outlet for urine, genital products, and for intestinal wastes. Next tailward arise some 40 pairs of nephric (kidney) tubules that constitute the mesonephros; these tubules join the same excretory ducts, hereafter called the mesonephric ducts. The two sets of mesonephric tubules serve as functioning kidneys until the 10th week.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      Each permanent kidney, or metanephros, develops still farther tailward. A so-called ureteric primordium buds off each mesonephric duct, near its hind end. The ureteric stem elongates and expands terminally, thereby forming the renal pelvis and calices; continued bushlike branching produces collecting ducts. The early ureteric bud invades a mass of nephrotome tissue. The branching collecting ducts progressively break this tissue up into tiny lumps, each of which becomes a long secretory tubule, or  <a class="v2" href="nephron">nephron</a>, and joins a nearby terminal twig of the duct system. Continued proliferation of ducts and nephric tissue produces over a million urine-producing tubules in each kidney.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      The blind caudal end of the endodermal hindgut absorbs the stem of each mesonephric duct, whereupon the remainder of the duct and the ureter acquire separate openings into the hindgut. This expanded region of the gut, now a potential receptacle for feces, urine, and reproductive products, is known as a cloaca. It next subdivides into a rectum behind and a urogenital sinus in front. The sinus, in turn, will specialize into the  <a class="v2" href="urinary bladder">urinary bladder</a> and the urethra. The  <a class="v2" href="prostate gland">prostate gland</a> develops as multiple buds from the urethra, close to the bladder.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--4--><b>Genital system</b></div>
	<div class="m1">      The genital organs begin to develop in the second month, but for a time sex is not grossly distinguishable. Also, a double set of male and female ducts arise, and not until later does the unneeded set decline. Hence, this period is commonly called the indifferent stage.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--5--><b>Gonads (<a class="v2" href="gonad">gonad</a>)</b></div>
	<div class="m1">      Sex glands develop in a pair of longitudinal ridges located alongside the mesentery, the anchoring fold of membrane to the gut. The primordial sex cells appear first in the cloacal wall, from which they migrate upward in the gut, pass through its mesentery, and finally invade the genital ridges, where they proliferate. The testes are the earliest type of gonad to organize. They begin by developing  <a class="v2" href="testis">testis</a> cords and a testis capsule. The cords radiate from one focal point at the periphery, and thin fibrous partitions segregate groups of the cords within wedge-shaped compartments. These cords do not gain channels and become semen-producing tubules until near the time of puberty. The ovaries (<a class="v2" href="ovary">ovary</a>) organize somewhat tardily by differentiating an outer portion, the cortex, and a central portion, the medulla. The cortex contains the primordial sex cells; these become surrounded by a layer of ordinary cells, thereby forming primary ovarian follicles. Both the testes and the ovaries undergo relative shifts from their early sites to lower positions in the body. But only the testes make a bodily descent; this is into the scrotum.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--5--><b>Genital ducts</b></div>
	<div class="m1">      In the male, a few mesonephric tubules on each side do not degenerate but link up with the neighbouring testis tubules. The converted mesonephric tubules and the retained mesonephric ducts become the male sex ducts. Near their terminations they outpouch seminal vesicles and then open into the urethra. In the female, a pair of ducts develops from the epithelium clothing the mesonephric ridges. These ducts, known as the uterine tubes, mostly parallel the courses of the mesonephric ducts, but at their lower ends they unite into a common tube that becomes the uterus and vagina.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--5--><b>External genitalia</b></div>
	<div class="m1">      Both sexes develop a genital tubercle (i.e., a knob) and a pair of urogenital folds flanked by a pair of genital swellings. At three months these rudiments begin to assume male or female characteristics. In the male, the tubercle and the united urogenital folds combine as the  <a class="v2" href="penis">penis</a>, thereby continuing the urethra to its end; the genital swellings shift toward the anus, fuse, and become the  <a class="v2" href="scrotum">scrotum</a>. In the female, the tubercle remains small, as the clitoris; (<a class="v2" href="clitoris">clitoris</a>) it does not contain the urethra. The urogenital folds remain unclosed as the lesser vulvar lips and are flanked by the unshifted and unfused genital swellings, or greater lips.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--4--><b>Coelom</b></div>
	<div class="m1">      The lateral mesoderm, beyond the somites and nephrotomes, splits into two layers: the somatic layer and, underlying the somatic layer, the splanchnic layer. The intervening space is the coelom. As the embryo's body folds off, its coelom becomes a single closed cavity. In it can be recognized, regionally, a provisional pericardial cavity (cavity for the heart), two pleural canals (for the lungs), and a peritoneal cavity (for the abdominal contents). A thick plate of mesoderm, the transverse septum, constitutes a partial partition just ahead of the developing liver. Two pairs of membranes grow out from the septum. One set separates the pericardial cavity from the two pleural cavities; these membranes later expand into the pericardium and enclose the heart. The other pair of membranes separates the pleural cavities from the peritoneal cavity of the abdomen. The definitive  <a class="v2" href="diaphragm">diaphragm</a> is a composite partition, much of which is furnished by the transverse septum; lesser contributions are from the lateral body walls and the paired membranes that separated the pleural and peritoneal cavities.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--3--><b><span class="ca" style="color:cadetblue">Endodermal derivatives</b></span></div>
	<div class="m1"><!--4--><b> <a class="v2" href="pharynx">pharynx</a></b></div>
	<div class="m1">      The tongue is a product of four branchial arches, whose ventral ends merge in its midplane. Papillae elevate from the surface, and taste buds arise as specializations within the covering epithelium of some of them. Pharyngeal pouches are early lateral expansions of the local endoderm, alternating with the branchial arches. The first pair elongate as the auditory tubes and tympanic cavities. The second pair mark the site of the tonsils. The third pair give rise to the halves of the thymus, and the third and fourth pairs produce the two sets of parathyroid glands. The  <a class="v2" href="thyroid gland">thyroid gland</a> buds off the pharyngeal floor in the midplane and at the level of the second branchial arches.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--4--><b>Digestive (<a class="v2" href="digestive system, human">digestive system, human</a>) tube</b></div>
	<div class="m1"><r>I1081.jpg</r> <i></i>As the embryo folds off, the endoderm is rolled in as the foregut and hindgut. Continued growth progressively closes both the midbody and the midgut (Figure 1O--&#62;<r>I1081.jpg</r>). The  <a class="v2" href="esophagus">esophagus</a> remains as a simple, straight tube. The  <a class="v2" href="stomach">stomach</a> grows faster on its dorsal side, thereby forming the bulging greater curvature; the stomach also rotates 90° so that its original dorsal and ventral borders come to lie left and right. The  <a class="v2" href="intestine">intestine</a> elongates faster than the trunk, so that its loops find temporary room by pushing into the umbilical cord. Later, the loops return, completing a rotation that gives the characteristic final placement of the small and large intestines.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      When the gut folds into a tube it is suspended by a sheetlike dorsal  <a class="v2" href="mesentery">mesentery</a>, or membranous fold. In the region of the stomach it forms an expansive pouch, the omental bursa. Secondary fusions of the bursa and of some of the rest of the mesentery with the body wall produce lines of attachment from stomach to rectum inclusive, different from the original midplane course. Such fusions also anchor firmly some parts of the tract. A ventral mesentery, beneath the gut, exists only in the region of the stomach and  <a class="v2" href="liver">liver</a>.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--4--><b>Major glands</b></div>
	<div class="m1">      The liver arises as a ventral outgrowth of the foregut that invades the early transverse septum. Although rapid growth causes it to bulge prominently away from this septum, it remains attached to the septum and hence to the definitive diaphragm. The differentiating glandular tissue takes the form of plates bathed by blood channels. The stem of the original liver bud becomes the common bile duct, whereas a secondary outgrowth produces the cystic duct and the gallbladder.</div>
	<div class="m1">&#160;</div>
	<div class="m1">      The pancreas takes its origin from a larger dorsal bud and a smaller ventral bud, both off the foregut. The two merge and their ducts communicate, but in man it is the lesser, ventral duct that becomes the stem outlet. Secretory acini are berrylike endings of the branching ducts. Pancreatic islets arise as special sprouts from the ducts; these differentiate into endocrine tissue that secretes insulin.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--4--><b>Respiratory system (<a class="v2" href="respiration, human">respiration, human</a>)</b></div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--5--><b>Nasal cavity</b></div>
	<div class="m1">      The first part of the respiratory system is ectodermal in origin. The olfactory sacs (see above Ectodermal derivatives: Sense organs (<a class="v2" href="human embryology">human embryology</a>)) become continuous secondarily with a passage captured from the primitive mouth cavity. This addition is produced by a horizontal partition, the palate. It arises from a pair of shelflike folds that grow out from the halves of the primitive upper jaw and then unite. The final nasal passage extends from the nostrils to the back of the pharynx.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--5--><b>Larynx, trachea, and lungs</b></div>
	<div class="m1">      A hollow lung bud grows off the floor of the endodermal pharynx, just caudal (tailward) to the pharyngeal pouches and in the midline. It has the form of a tube with an expanded end. The entrance to this tube is the  <a class="v2" href="glottis">glottis</a>, and the region about it becomes the  <a class="v2" href="larynx">larynx</a>. The tube proper represents the trachea. Its terminal expansion divides into two branches, and these tubes elongate as the primary bronchi. Continued growth and budding produce two side branches from the right bronchus and one from the left. These branches and the blind ends of the two parent bronchi indicate the future plan of the lungs, with three right lobes and two left lobes. Continued branchings, through the sixth month, produce bronchioles of different orders. In the final months the smaller ducts and early respiratory alveoli (air sacs) appear, the lungs losing their previous glandular appearance and also becoming highly vascular. Until breathing distends the lungs, these organs remain relatively small.</div>
	<div class="m1">&#160;</div>
	<div class="m1"><span class="ca" style="color:dimgray"><i>Leslie B. Arey</i></span>  <b>Ed.</b></div>
	<div class="m1">&#160;</div>
	<div class="m1"><!--2--><i><b><span class="ca" style="color:darkcyan">Additional Reading</b></i></span></div>
	<div class="m1"> Overviews of the topic are provided by Manuel Hernández (M. Hernández Rodríguez) and Jesús Argente (eds.), <i>Human Growth: Basic and Clinical Aspects</i> (1992), conference proceedings; Frank Falkner and J.M. Tanner (eds.), <i>Human Growth</i>, 3 vol. (1978–79), with vol. 1 and 3 in a 2nd ed. (1986); and Esmail Meisami and Paola S. Timiras (eds.), <i>Handbook of Human Growth and Developmental Biology</i>, 3 vol. in 7 (1988–90).Explorations of human embryology in particular include Stephen G. Gilbert, <i>Pictorial Human Embryology</i> (1989), an atlas containing colourful line illustrations of histological sections and stages of human embryos, for the health professional and the general reader; Keith L. Moore and T.V.N. Persaud, <i>The Developing Human: Clinically Oriented Embryology</i>, 5th ed. (1993); Jan Langman, <i>Langman's Medical Embryology</i>, 7th ed. by T.W. Sadler (1995), a concise presentation; and Jan S. Zagon and Theodore A. Slotkin (eds.), <i>Maternal Substance Abuse and the Developing Nervous System</i> (1992), describing the effects of maternal drug consumption on fetal, neonatal infant, and subsequent adult nervous systems.</div><br/>
	
trivial card
	It's a duplicate key<br/><br/>
