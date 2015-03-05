#NAME "Sample DSL Dictionary"
#INDEX_LANGUAGE	"English"
#CONTENTS_LANGUAGE	"English"

trivial card
  Trivially simple card. The body of the card starts with spaces or TABs, that's all.
  
trivial card with markup
  Trivially simple card. [m3]Very simple.[/m]
  [m2]Two left margin tags[/m][m2] in one line.[/m] and line with no tags.
  [m2]Math expressions: 5 < 3 < 2, 2 <=> 2[/m]
  This is [p]abbr.[/p]
  [c]Text with default highlighted color[/c]
  [c blue]Text with blue highlighted color[/c]
  [t]This is transcription[/t]
  Stress[']e[/']d word
  [com]This text is a comment[/com]
  [trn][*]Translation zone line[/*][/trn]
  [!trs]Don't index this line[/!trs]
  [ex]Example line[/ex]
  [ex testatr]Example line with test attribute[/ex]
  [lang id=1033]Language 1033 line[/lang]
  [lang]Language line with no id[/lang]
  Line with braces \(\) \{ \}
  One more line.
  \ 
specialchars
specialcharssecond
	Escaped:
	
		\ 
		\\
		\[
		\]
		\{
		\}
		Comments included in \{\{\}\} are invisible: {{DSL comments that are invisible}}
		\@ (At sign)
		\^ (Circumflex accent)
		\~ (Tilde)
		\_
		\+
		\-
		\%
		\$
		\&
		\*
		\<
		\>
		\!
		\?
		\# (Hash sign)
		
	Unescaped:
	
	
		Escape is not shown \
		Empty square brackets break the view (not included)
		Curly braces are shown the same way { }.
		Comments included in \{\{\}\} are invisible: {{DSL comments that are invisible}}
		At sign breaks the view (not included)
		Circumflex accent is shown the same way ^
		Tilde is replaced by the headword ~
		_
		+
		-
		%
		$
		&
		*
		<
		>
		!
		?
		Hash sign breaks the view (not included)
		
little with subentries, since lingvo x3
   1) small
   [m1][*]
   @ little one
   baby
   @
   [/*][/m]
   2) tiny, short
   3) trifle
   [m1][*]
   @ little by little
   gradually
   @
   [/*][/m]
	
-ae1
-ae1 {duplicate}
-ae2 {with the long name in the article}
-ae2 with \{braces\}
-ae2 with \{braces\} and {optional part}
-ae2 with \(parentheses\)
-ae2(changing)
-ae2(changing) two (times)
-ae2(changing) two (times) with {optional \(escaped\)} and \(escaped\) 
-ae2 {CO[sub]}2{[/sub]}
-ae3 {with the long name in the article} partially {supposed to be} excluded
        [m1][i][c][trn]suffix[/trn][/c][/i][/m]
        [m1]1) [trn]used in names of animal and plant families and other groups[/trn][/m]
        [m1][*][trn][ex]Felidae[/ex][/trn][/*][/m]
        [m1][*][trn][ex]Gymnospermae[/ex][/trn][/*][/m]
        [m1]2) [trn]used instead of [i]-as[/i] in the plural of many nonnaturalized or unfamiliar nouns ending in [i]-a[/i] derived from Latin or Greek[/trn][/m]
        [m1][*][trn][ex]alumnae[/ex][/trn][/*][/m]
        [m1][*][trn][ex]larvae[/ex][/trn][/*][/m]
        [m1][trn]forming plural nouns:[/trn][/m]
        [m1][*][b]Origin:[/b][/*][/m]
        [m2][*][com][lang id=1033]representing Latin plural, or the Greek plural ending [i]-ai[/i] of some nouns[/lang][/com][/*][/m]

word without optional part 1
	Body of the card 1

word without optional part 2
	Body of the card 2

word with optional part { - some optional part}
	[m1]This is the body of the card[/m]

sample entry
example
sample {unsorted part} card 1
sample {unsorted part}card 2
sample{unsorted part} card 3
sample {unsorted part} card 4 {at the end}
{the }sample headword
   •• [b]Special symbols that might require escaping:[/b]
   1. Tilde: I love this \~. • I love this ~. (The tilde replaces the first headword.)
   2. At-sign: \@ ([b]Note:[/b] Sub-entries)
   3. Brackets: \[ \] \{ \}
   \ 
   •• [b]Basic formatting:[/b]
   \[b\]bold text\[/b\] • [b]bold text[/b]
   \[i\]italics\[/i\] • [i]italics[/i]
   \[u\]underlined text\[/u\] • [u]underlined text[/u]
   CO\[sub\]2\[/sub\] • CO[sub]2[/sub] • subscript
   e=mc\[sup\]2\[/sup\] • e=mc[sup]2[/sup] • superscript
   \ 
   •• [b]Indents:[/b]
   [m0]\[m0\] - sets the left paragraph margin to 0 spaces The corresponding closing tag of the paragraph is. \[/m\][/m]
   [m1]\[m1\] - sets the left paragraph margin to 1 spaces The corresponding closing tag of the paragraph is. \[/m\][/m]
   [m2]\[m2\] - sets the left paragraph margin to 2 spaces The corresponding closing tag of the paragraph is. \[/m\][/m]
   [m3]\[m3\] - sets the left paragraph margin to 3 spaces The corresponding closing tag of the paragraph is. \[/m\][/m]
   [m4]\[m4\] - sets the left paragraph margin to 4 spaces The corresponding closing tag of the paragraph is. \[/m\][/m]
   [m5]\[m5\] - sets the left paragraph margin to 5 spaces The corresponding closing tag of the paragraph is. \[/m\][/m]
   [m6]\[m6\] - sets the left paragraph margin to 6 spaces The corresponding closing tag of the paragraph is. \[/m\][/m]
   [m7]\[m7\] - sets the left paragraph margin to 7 spaces The corresponding closing tag of the paragraph is. \[/m\][/m]
   [m8]\[m8\] - sets the left paragraph margin to 8 spaces The corresponding closing tag of the paragraph is. \[/m\][/m]
   [m9]\[m9\] - sets the left paragraph margin to 9 spaces The corresponding closing tag of the paragraph is. \[/m\][/m]
   \ 
   •• [b]Zones:[/b]
   \{\{Comments are inside curly brackets\}\} • {{This is a comment which should be invisible.}}
   \[*\]\[ex\]example\[/ex\]\[/*\] • [*][ex]example[/ex][/*] (Lingvo specific: It is recommended to add \[*\] and \[/*\] around example).
   \[s\]sample.bmp\[/s\] • [s]sample.bmp[/s] • multimedia zone (used to add pictures or sound files into a dictionary entries ).
   \[p\]v.\[/p\] • [p]v.[/p] • labels are shown in a different colour
   str\['\]e\[/'\]ss  • str[']e[/']ss • A str[']e[/']ssed v[']o[/']wel in a w[']o[/']rd.
   \ 
   •• [b]Links:[/b]
   \<<typcial card\>> • <<typcial card>> • Recommended way to create a hyperlink to another card.
   \[ref\]link1\[/ref\] • [ref]link1[/ref]  • hyperlink to another card, not recommended, deprecated.
   \[url\]http://sample.org/\[/url\] • [url]http://sample.org/[/url] • link to a Web page
   \ 
   •• [b]Colors:[/b]
   \[c blue\]Blue text\[/c\] • [c blue]Blue text[/c]
   \   {{ Here's how you insert an empty space: slash and space after it }}
   Supported colour names:
	aliceblue: [c aliceblue]plain [u]underscore[/u] [i]italic[/i] [b]aliceblue[/c][/b]
	antiquewhite: [c antiquewhite]plain [u]underscore[/u] [i]italic[/i] [b]antiquewhite[/c][/b]
	aqua: [c cyan]plain [u]underscore[/u] [i]italic[/i] [b]aqua[/c][/b]
	aquamarine: [c aquamarine]plain [u]underscore[/u] [i]italic[/i] [b]aquamarine[/c][/b]
	azure: [c azure]plain [u]underscore[/u] [i]italic[/i] [b]azure[/c][/b]
	beige: [c beige]plain [u]underscore[/u] [i]italic[/i] [b]beige[/c][/b]
	bisque: [c bisque]plain [u]underscore[/u] [i]italic[/i] [b]bisque[/c][/b]
	blanchedalmond: [c blanchedalmond]plain [u]underscore[/u] [i]italic[/i] [b]blanchedalmond[/c][/b]
	blue: [c blue]plain [u]underscore[/u] [i]italic[/i] [b]blue[/c][/b]
	blueviolet: [c blueviolet]plain [u]underscore[/u] [i]italic[/i] [b]blueviolet[/c][/b]
	brown: [c brown]plain [u]underscore[/u] [i]italic[/i] [b]brown[/c][/b]
	burlywood: [c burlywood]plain [u]underscore[/u] [i]italic[/i] [b]burlywood[/c][/b]
	cadetblued: [c cadetblue]plain [u]underscore[/u] [i]italic[/i] [b]cadetblue[/c][/b]
	chartreuse: [c chartreuse]plain [u]underscore[/u] [i]italic[/i] [b]chartreuse[/c][/b]
	chocolate: [c chocolate]plain [u]underscore[/u] [i]italic[/i] [b]chocolate[/c][/b]
	coral: [c coral]plain [u]underscore[/u] [i]italic[/i] [b]coral[/c][/b]
	cornflower: [c cornflower]plain [u]underscore[/u] [i]italic[/i] [b]cornflower[/c][/b]
	cornsilk: [c cornsilk]plain [u]underscore[/u] [i]italic[/i] [b]cornsilk[/c][/b]
	crimson: [c crimson]plain [u]underscore[/u] [i]italic[/i] [b]crimson[/c][/b]
	cyan: [c cyan]plain [u]underscore[/u] [i]italic[/i] [b]cyan[/c][/b]
	darkblue: [c darkblue]plain [u]underscore[/u] [i]italic[/i] [b]darkblue[/c][/b]
	darkcyan: [c darkcyan]plain [u]underscore[/u] [i]italic[/i] [b]darkcyan[/c][/b]
	darkgoldenrod: [c darkgoldenrod]plain [u]underscore[/u] [i]italic[/i] [b]darkgoldenrod[/c][/b]
	darkgray: [c darkgray]plain [u]underscore[/u] [i]italic[/i] [b]darkgray[/c][/b]
	darkgreen: [c darkgreen]plain [u]underscore[/u] [i]italic[/i] [b]darkgreen[/c][/b]
	darkkhaki: [c darkkhaki]plain [u]underscore[/u] [i]italic[/i] [b]darkkhaki[/c][/b]
	darkmagenta: [c darkmagenta]plain [u]underscore[/u] [i]italic[/i] [b]darkmagenta[/c][/b]
	darkolivegreen: [c darkolivegreen]plain [u]underscore[/u] [i]italic[/i] [b]darkolivegreen[/c][/b]
	darkorange: [c darkorange]plain [u]underscore[/u] [i]italic[/i] [b]darkorange[/c][/b]
	darkorchid: [c darkorchid]plain [u]underscore[/u] [i]italic[/i] [b]darkorchid[/c][/b]
	darkred: [c darkred]plain [u]underscore[/u] [i]italic[/i] [b]darkred[/c][/b]
	darksalmon: [c darksalmon]plain [u]underscore[/u] [i]italic[/i] [b]darksalmon[/c][/b]
	darkseagreen: [c darkseagreen]plain [u]underscore[/u] [i]italic[/i] [b]darkseagreen[/c][/b]
	darkslateblue: [c darkslateblue]plain [u]underscore[/u] [i]italic[/i] [b]darkslateblue[/c][/b]
	darkslategray: [c darkslategray]plain [u]underscore[/u] [i]italic[/i] [b]darkslategray[/c][/b]
	darkturquoise: [c darkturquoise]plain [u]underscore[/u] [i]italic[/i] [b]darkturquoise[/c][/b]
	darkviolet: [c darkviolet]plain [u]underscore[/u] [i]italic[/i] [b]darkviolet[/c][/b]
	deeppink: [c deeppink]plain [u]underscore[/u] [i]italic[/i] [b]deeppink[/c][/b]
	deepskyblue: [c deepskyblue]plain [u]underscore[/u] [i]italic[/i] [b]deepskyblue[/c][/b]
	dimgray: [c dimgray]plain [u]underscore[/u] [i]italic[/i] [b]dimgray[/c][/b]
	dodgerblue: [c dodgerblue]plain [u]underscore[/u] [i]italic[/i] [b]dodgerblue[/c][/b]
	firebrick: [c firebrick]plain [u]underscore[/u] [i]italic[/i] [b]firebrick[/c][/b]
	floralwhite: [c floralwhite]plain [u]underscore[/u] [i]italic[/i] [b]floralwhite[/c][/b]
	forestgreen: [c forestgreen]plain [u]underscore[/u] [i]italic[/i] [b]forestgreen[/c][/b]
	fuchsia: [c magenta]plain [u]underscore[/u] [i]italic[/i] [b]fuchsia[/c][/b]
	gainsboro: [c gainsboro]plain [u]underscore[/u] [i]italic[/i] [b]gainsboro[/c][/b]
	ghostwhite: [c ghostwhite]plain [u]underscore[/u] [i]italic[/i] [b]ghostwhite[/c][/b]
	gold: [c gold]plain [u]underscore[/u] [i]italic[/i] [b]gold[/c][/b]
	goldenrod: [c goldenrod]plain [u]underscore[/u] [i]italic[/i] [b]goldenrod[/c][/b]
	gray: [c gray]plain [u]underscore[/u] [i]italic[/i] [b]gray[/c][/b]
	green: [c green]plain [u]underscore[/u] [i]italic[/i] [b]green[/c][/b]
	greenyellow: [c greenyellow]plain [u]underscore[/u] [i]italic[/i] [b]greenyellow[/c][/b]
	honeydew: [c honeydew]plain [u]underscore[/u] [i]italic[/i] [b]honeydew[/c][/b]
	hotpink: [c hotpink]plain [u]underscore[/u] [i]italic[/i] [b]hotpink[/c][/b]
	indianred: [c indianred]plain [u]underscore[/u] [i]italic[/i] [b]indianred[/c][/b]
	indigo: [c indigo]plain [u]underscore[/u] [i]italic[/i] [b]indigo[/c][/b]
	ivory: [c ivory]plain [u]underscore[/u] [i]italic[/i] [b]ivory[/c][/b]
	khaki: [c khaki]plain [u]underscore[/u] [i]italic[/i] [b]khaki[/c][/b]
	lavender: [c lavender]plain [u]underscore[/u] [i]italic[/i] [b]lavender[/c][/b]
	lavenderblush: [c lavenderblush]plain [u]underscore[/u] [i]italic[/i] [b]lavenderblush[/c][/b]
	lawngreen: [c lawngreen]plain [u]underscore[/u] [i]italic[/i] [b]lawngreen[/c][/b]
	lemonchiffon: [c lemonchiffon]plain [u]underscore[/u] [i]italic[/i] [b]lemonchiffon[/c][/b]
	lightblue: [c lightblue]plain [u]underscore[/u] [i]italic[/i] [b]lightblue[/c][/b]
	lightcoral: [c lightcoral]plain [u]underscore[/u] [i]italic[/i] [b]lightcoral[/c][/b]
	lightcyan: [c lightcyan]plain [u]underscore[/u] [i]italic[/i] [b]lightcyan[/c][/b]
	lightgoldenrodyellow: [c lightgoldenrodyellow]plain [u]underscore[/u] [i]italic[/i] [b]lightgoldenrodyellow[/c][/b]
	lightgreen: [c lightgreen]plain [u]underscore[/u] [i]italic[/i] [b]lightgreen[/c][/b]
	lightgray: [c lightgray]plain [u]underscore[/u] [i]italic[/i] [b]lightgray[/c][/b]
	lightpink: [c lightpink]plain [u]underscore[/u] [i]italic[/i] [b]lightpink[/c][/b]
	lightsalmon: [c lightsalmon]plain [u]underscore[/u] [i]italic[/i] [b]lightsalmon[/c][/b]
	lightseagreen: [c lightseagreen]plain [u]underscore[/u] [i]italic[/i] [b]lightseagreen[/c][/b]
	lightskyblue: [c lightskyblue]plain [u]underscore[/u] [i]italic[/i] [b]lightskyblue[/c][/b]
	lightslategray: [c lightslategray]plain [u]underscore[/u] [i]italic[/i] [b]lightslategray[/c][/b]
	lightsteelblue: [c lightsteelblue]plain [u]underscore[/u] [i]italic[/i] [b]lightsteelblue[/c][/b]
	lightyellow: [c lightyellow]plain [u]underscore[/u] [i]italic[/i] [b]lightyellow[/c][/b]
	lime: [c lime]plain [u]underscore[/u] [i]italic[/i] [b]lime[/c][/b]
	limegreen: [c limegreen]plain [u]underscore[/u] [i]italic[/i] [b]limegreen[/c][/b]
	linen: [c linen]plain [u]underscore[/u] [i]italic[/i] [b]linen[/c][/b]
	magenta: [c magenta]plain [u]underscore[/u] [i]italic[/i] [b]magenta[/c][/b]
	maroon: [c maroon]plain [u]underscore[/u] [i]italic[/i] [b]maroon[/c][/b]
	mediumaquamarine: [c mediumaquamarine]plain [u]underscore[/u] [i]italic[/i] [b]mediumaquamarine[/c][/b]
	mediumblue: [c mediumblue]plain [u]underscore[/u] [i]italic[/i] [b]mediumblue[/c][/b]
	mediumorchid: [c mediumorchid]plain [u]underscore[/u] [i]italic[/i] [b]mediumorchid[/c][/b]
	mediumpurple: [c mediumpurple]plain [u]underscore[/u] [i]italic[/i] [b]mediumpurple[/c][/b]
	mediumseagreen: [c mediumseagreen]plain [u]underscore[/u] [i]italic[/i] [b]mediumseagreen[/c][/b]
	mediumslateblue: [c mediumslateblue]plain [u]underscore[/u] [i]italic[/i] [b]mediumslateblue[/c][/b]
	mediumspringgreen: [c mediumspringgreen]plain [u]underscore[/u] [i]italic[/i] [b]mediumspringgreen[/c][/b]
	mediumturquoise: [c mediumturquoise]plain [u]underscore[/u] [i]italic[/i] [b]mediumturquoise[/c][/b]
	mediumvioletred: [c mediumvioletred]plain [u]underscore[/u] [i]italic[/i] [b]mediumvioletred[/c][/b]
	midnightblue: [c midnightblue]plain [u]underscore[/u] [i]italic[/i] [b]midnightblue[/c][/b]
	mintcream: [c mintcream]plain [u]underscore[/u] [i]italic[/i] [b]mintcream[/c][/b]
	mistyrose: [c mistyrose]plain [u]underscore[/u] [i]italic[/i] [b]mistyrose[/c][/b]
	moccasin: [c moccasin]plain [u]underscore[/u] [i]italic[/i] [b]moccasin[/c][/b]
	navajowhite: [c navajowhite]plain [u]underscore[/u] [i]italic[/i] [b]navajowhite[/c][/b]
	navy: [c navy]plain [u]underscore[/u] [i]italic[/i] [b]navy[/c][/b]
	oldlace: [c oldlace]plain [u]underscore[/u] [i]italic[/i] [b]oldlace[/c][/b]
	olive: [c olive]plain [u]underscore[/u] [i]italic[/i] [b]olive[/c][/b]
	olivedrab: [c olivedrab]plain [u]underscore[/u] [i]italic[/i] [b]olivedrab[/c][/b]
	orange: [c orange]plain [u]underscore[/u] [i]italic[/i] [b]orange[/c][/b]
	orangered: [c orangered]plain [u]underscore[/u] [i]italic[/i] [b]orangered[/c][/b]
	orchid: [c orchid]plain [u]underscore[/u] [i]italic[/i] [b]orchid[/c][/b]
	palegoldenrod: [c palegoldenrod]plain [u]underscore[/u] [i]italic[/i] [b]palegoldenrod[/c][/b]
	palegreen: [c palegreen]plain [u]underscore[/u] [i]italic[/i] [b]palegreen[/c][/b]
	paleturquoise: [c paleturquoise]plain [u]underscore[/u] [i]italic[/i] [b]paleturquoise[/c][/b]
	palevioletred: [c palevioletred]plain [u]underscore[/u] [i]italic[/i] [b]palevioletred[/c][/b]
	papayawhip: [c papayawhip]plain [u]underscore[/u] [i]italic[/i] [b]papayawhip[/c][/b]
	peachpuff: [c peachpuff]plain [u]underscore[/u] [i]italic[/i] [b]peachpuff[/c][/b]
	peru: [c peru]plain [u]underscore[/u] [i]italic[/i] [b]peru[/c][/b]
	pink: [c pink]plain [u]underscore[/u] [i]italic[/i] [b]pink[/c][/b]
	plum: [c plum]plain [u]underscore[/u] [i]italic[/i] [b]plum[/c][/b]
	powderblue: [c powderblue]plain [u]underscore[/u] [i]italic[/i] [b]powderblue[/c][/b]
	purple: [c purple]plain [u]underscore[/u] [i]italic[/i] [b]purple[/c][/b]
	red: [c red]plain [u]underscore[/u] [i]italic[/i] [b]red[/c][/b]
	rosybrown: [c rosybrown]plain [u]underscore[/u] [i]italic[/i] [b]rosybrown[/c][/b]
	royalblue: [c royalblue]plain [u]underscore[/u] [i]italic[/i] [b]royalblue[/c][/b]
	saddlebrown: [c saddlebrown]plain [u]underscore[/u] [i]italic[/i] [b]saddlebrown[/c][/b]
	salmon: [c salmon]plain [u]underscore[/u] [i]italic[/i] [b]salmon[/c][/b]
	sandybrown: [c sandybrown]plain [u]underscore[/u] [i]italic[/i] [b]sandybrown[/c][/b]
	seagreen: [c seagreen]plain [u]underscore[/u] [i]italic[/i] [b]seagreen[/c][/b]
	seashell: [c seashell]plain [u]underscore[/u] [i]italic[/i] [b]seashell[/c][/b]
	sienna: [c sienna]plain [u]underscore[/u] [i]italic[/i] [b]sienna[/c][/b]
	silver: [c silver]plain [u]underscore[/u] [i]italic[/i] [b]silver[/c][/b]
	skyblue: [c skyblue]plain [u]underscore[/u] [i]italic[/i] [b]skyblue[/c][/b]
	slateblue: [c slateblue]plain [u]underscore[/u] [i]italic[/i] [b]slateblue[/c][/b]
	slategray: [c slategray]plain [u]underscore[/u] [i]italic[/i] [b]slategray[/c][/b]
	snow: [c snow]plain [u]underscore[/u] [i]italic[/i] [b]snow[/c][/b]
	springgreen: [c springgreen]plain [u]underscore[/u] [i]italic[/i] [b]springgreen[/c][/b]
	steelblue: [c steelblue]plain [u]underscore[/u] [i]italic[/i] [b]steelblue[/c][/b]
	tan: [c tan]plain [u]underscore[/u] [i]italic[/i] [b]tan[/c][/b]
	teal: [c teal]plain [u]underscore[/u] [i]italic[/i] [b]teal[/c][/b]
	thistle: [c thistle]plain [u]underscore[/u] [i]italic[/i] [b]thistle[/c][/b]
	tomato: [c tomato]plain [u]underscore[/u] [i]italic[/i] [b]tomato[/c][/b]
	turquoise: [c turquoise]plain [u]underscore[/u] [i]italic[/i] [b]turquoise[/c][/b]
	violet: [c violet]plain [u]underscore[/u] [i]italic[/i] [b]violet[/c][/b]
	wheat: [c wheat]plain [u]underscore[/u] [i]italic[/i] [b]wheat[/c][/b]
	white: [c white]plain [u]underscore[/u] [i]italic[/i] [b]white[/c][/b]
	whitesmoke: [c whitesmoke]plain [u]underscore[/u] [i]italic[/i] [b]whitesmoke[/c][/b]
	yellow: [c yellow]plain [u]underscore[/u] [i]italic[/i] [b]yellow[/c][/b]
	yellowgreen: [c yellowgreen]plain [u]underscore[/u] [i]italic[/i] [b]yellowgreen[/c][/b]

typical card
   This is a typical card taken from Lingvo Documentation:
   \ 
   [b]1.[/b] [p]v.[/p]
   [m1]1) [p][trn]common.[/p] refuse [com]([i]from something[/i])[/com], stop [com]([i]something, doing something[/i])[/com]; leave [com]([i]something[/i])[/com][/trn][/m]
   [m2][*][ex][lang id=1033]to abandon a Bill[/lang] — refuse to move with a law project[/ex][/*][/m]
   [m2][*][ex][lang id=1033]to abandon a claim \[a right, an action\][/lang] — refuse claims \[of rights, of civil actions\][/ex][/*][/m]
   [m2][*][ex][lang id=1033]to abandon (all) hope[/lang] — leave (any) hope[/ex][/*][/m]
   [m2][*][b]See:[/b][/*][/m]
   [m2][*][com][lang id=1033]abandoned property[/lang][/com][/*][/m]
   [m1]2) [p][trn]ec.[/p] закрывать; консервировать [com]([i]напр., транспортную линию, производство и т. п.[/i])[/com][/trn][/m]
   [m2][*][ex][lang id=1033]to abandon the transportation services[/lang] — заморозить транспортное обслуживание[/ex][/*][/m]
   [m1]3) [p][trn]общ.[/p] оставлять, уходить [com]([i]с поста и т. д.[/i])[/com]; покидать, оставлять [com]([i]что-л. или кого-л.[/i])[/com][/trn][/m]
   [m2][*][ex][lang id=1033]to be forced to abandon a position[/lang] — быть вынужденным оставить должность[/ex][/*][/m]
   [m2][*][ex][lang id=1033]He abandoned his family and went abroad.[/lang] — Он оставил свою семью и поехал за границу.[/ex][/*][/m]
   [m2][*][ex][lang id=1033]He abandoned his car and tried to escape on foot.[/lang] — Он бросил машину и попытался ускользнуть пешком.[/ex][/*][/m]
   [m2][*][b]See:[/b][/*][/m]
   [m2][*][ref dict="Marketing (En-Ru)"]abandonment[/ref][/*][/m]
   [b]2.[/b] [p]noun.[/p]
   [m1][p][trn]abst.[/p] импульсивность; развязанность, несдержанность[/trn][/m]
   [m2][*][ex][lang id=1033]to do smth. with in complete abandon[/lang] — делать что-л., совершенно забыв обо всем[/ex][/*][/m]

human embryology
	[m1]\ [/m]
	[m0][c darkmagenta][b]▪ biology[/b][/c][/m]
	[m1]{{2}}[i][b][c darkcyan]Introduction[/b][/i][/c][/m]
	[m1]\ [/m]
	[m1]      the process encompassing the period from the formation of an embryo, through the development of a fetus, to birth.[/m]
	[m1]\ [/m]
	[m1]      The  <<human body>>, like that of most animals, develops from a single cell produced by the union of a male sex cell and a female sex cell. Human development follows closely the basic vertebrate pattern, and it departs only in certain details from the type specifically characteristic of mammals. A prenatal period, in which most of the developmental advances occur, is followed by a long postnatal period. Only at about the age of 25 are the last progressive changes completed.[/m]
	[m1]\ [/m]
	[m1]{{2}}[i][b][c darkcyan]Normal development[/b][/i][/c][/m]
	[m1]\ [/m]
	[m1]{{3}}[b][c cadetblue]From fertilization to placentation[/b][/c][/m]
	[m1]{{4}}[b] <<fertilization>>[/b][/m]
	[m1]      The development and liberation of the male and female gametes (<<gamete>>) are steps preparatory to their union through the process of fertilization. Random movements first bring some spermatozoa into contact with follicle cells adhering to the secondary oocyte, which still lies high in the uterine tube. The sperm then propel themselves past the follicle cells and attach to the surface of the gelatinous zona pellucida enclosing the oocyte. Some sperm heads successfully penetrate this capsule by means of an enzyme they secrete,  <<hyaluronidase>>, but only one sperm makes contact with the cell membrane and cytoplasm of the oocyte and proceeds farther. This is because the invading sperm head releases a substance that initiates surface changes in the oocyte cytoplasm that other competitors cannot master.[/m]
	[m1]\ [/m]
	[m1]      The successful sperm is engulfed by a conical protrusion of the oocyte cytoplasm and is drawn inward. Once within the periphery of the oocyte, the sperm advances toward the centre of the cytoplasm; the head swells and converts into a typical nucleus, now called the male pronucleus, and the tail detaches. It is during the progress of these events that the oocyte initiates its final maturation division. Following the separation of the second polar body, the oocyte nucleus reconstitutes typically and is then called the female pronucleus of the ripe egg. It is now ready to unite with its male counterpart and thereby consummate the total events of fertilization.[/m]
	[m1]\ [/m]
	[m1]      The two pronuclei next approach, meet midway in the egg cytoplasm, and lose their nuclear membranes. Each resolves its diffuse chromatin material into a complete, single set of 23 chromosomes (<<chromosome>>). Centrioles (complex particles involved in cell division), apparently supplied by the sperm, appear, and a mitotic spindle organizes with the two sets of chromosomes arranged midway on it—ready to proceed with a typical  <<mitosis>>. This climax in the events of fertilization creates a joint product named the zygote. It contains all the essential factors for the development of a new individual.[/m]
	[m1]\ [/m]
	[m1]      The fundamental results of fertilization are the following: (1) reassociation of a male and female set of chromosomes (thus restoring the full number and providing the basis for biparental inheritance and for variation); (2) establishment of the mechanism of sex determination for the new individual (this depending on whether the male set of chromosomes included the X or the Y chromosome); (3) activation of the zygote, initiating a beginning toward the production of a new individual.[/m]
	[m1]\ [/m]
	[m1]{{4}}[b] <<cleavage>> and blastulation[/b][/m]
	[m1]\ [/m]
	[m1]{{5}}[b]Cleavage[/b][/m]
	[m1][s]I1077.jpg[/s] [i][/i]The onset of  <<mitosis>> (ordinary cell proliferation by division) in the activated zygote is a first step toward development in the ordinary sense of that term, and the cells so produced are the first external sign of future body building. To this end, the relatively enormous zygote directly subdivides into many smaller cells of conventional size, suitable as early building units for the future organism. This process is called cleavage and the resulting cells are blastomeres (Figures 1A–D-->[s]I1077.jpg[/s]). The tendency for the progressive increase in cell numbers to follow a doubling sequence is soon disturbed and then lost. Each blastomere receives the full complement of paternal and maternal chromosomes.[/m]
	[m1]\ [/m]
	[m1]      Subdivision of the zygote into blastomeres begins while it is still high in the uterine tube. The cohering blastomeres are transported downward chiefly, at least, by muscular contractions of the tubal wall. Such transport is relatively rapid until the lower end of the tube is reached, and here cleavage continues for about two days before the multicellular cluster is expelled into the uterus. The full reason for this delay is not clear, but it serves to retain the cleaving blastomeres until the uterine lining is suitably prepared to receive its prospective guest.[/m]
	[m1]\ [/m]
	[m1]      Since the human egg contains little inert yolk material, and since this is distributed rather evenly throughout the cytoplasm, the daughter cells of each mitosis are practically equal in size and composition. This type of cleavage is known as total, equal cleavage. The sticky blastomeres adhere and the cluster is still retained for a time within the gelatinous capsule—the zona pellucida—that had enclosed the growing and ovulated oocyte. There is no growth in the rapidly dividing blastomeres, so that the total mass of living substance does not increase during the cleavage period.[/m]
	[m1]\ [/m]
	[m1]{{5}}[b]Morula and blastocyst[/b][/m]
	[m1][s]I1077.jpg[/s] [i][/i][s]I1077.jpg[/s] [i][/i]By the fourth day after ovulation, a cluster of about 12 blastomeres passes from the uterine tube into the uterus. When the cluster numbers 12 to 16 blastomeres it is called a  <<morula>> (Figure 1D-->[s]I1077.jpg[/s]). By the time some 30 blastomeres have been produced, pools of clear fluid accumulate between some of the internal cells, and these spaces soon coalesce into a common subcentral cavity. The resulting hollow cellular ball is a blastula of a particular type that occurs in mammals and is called a blastocyst; (<<blastocyst>>) its cavity is the blastocoel (Figure 1E, F-->[s]I1077.jpg[/s]).[/m]
	[m1]\ [/m]
	[m1]      An internal cellular cluster, eccentric in position and now named the inner cell mass, will develop into the embryo. The external capsule of smaller cells, enveloping the segregated internal cluster, constitutes the trophoblast. It will contribute to the formation of a  <<placenta>>. During its stay within the uterine cavity, the blastocyst loses its gelatinous capsule, imbibes fluid, and expands to a diameter of 0.2 millimetre (0.008 inch); this is nearly twice the diameter of the zygote at the start of cleavage. Probably several hundred blastomeres have formed before the blastocyst attaches to the uterine (<<uterus>>) lining.[/m]
	[m1]\ [/m]
	[m1]{{4}}[b]Implantation and placentation[/b][/m]
	[m1][s]I1077.jpg[/s] [i][/i]Late in the sixth day after ovulation the naked, sticky blastocyst comes into contact with the uterine lining and adheres to it (Figure 1F-->[s]I1077.jpg[/s]). The site of attachment is variable and not predetermined. The uterine lining has already been preparing, under the influence of ovarian hormones, for the reception of the blastocyst. Among these preparations has been the elaboration and expulsion, by the uterine glands, of a secretion that serves as nourishment for the blastocyst, both when it is free and during its implantation. Directly after blastocyst attachment come its establishment within the thickened uterine lining and the participation of its trophoblastic capsule in the differentiation of a placenta, a structure that enables the developing embryo to enter into a prompt physiological dependence on the mother.[/m]
	[m1]\ [/m]
	[m1]{{5}}[b] <<implantation>>[/b][/m]
	[m1][s]I1078.jpg[/s] [i][/i][s]I1078.jpg[/s] [i][/i]The trophoblast of the blastocyst exerts an enzymic, destructive influence on the swollen uterine lining, leading to erosion of both the superficial  <<epithelium>> of the uterine lining and also its deeper, cellular connective tissue (Figure 1G-->[s]I1078.jpg[/s]). This early stage of invasion ends in a few days; the blastocyst is then completely buried within a more superficial and compact layer of the total uterine lining (Figure 1G, H-->[s]I1078.jpg[/s]). While the blastocyst is completing this phase of implantation, its original shell of cellular trophoblast steadily proliferates a multitude of cells that lose their outermost membranes and merge. The result is a thick peripheral layer consisting of a common mass of cytoplasm in which many nuclei are embedded. This external investment is called syncytial trophoblast.[/m]
	[m1]\ [/m]
	[m1][s]I1078.jpg[/s] [i][/i][s]I1080.jpg[/s] [i][/i]The implanted blastocyst next proceeds to establish itself as dependent upon the uterus. The syncytial trophoblast becomes a spongy shell containing irregular cavities (Figure 1H-->[s]I1078.jpg[/s]). This expanding mass destroys cellular  <<connective tissue>> and glands encountered in its path. Both the cellular and derivative syncytial trophoblast have the capacity of ingesting such tissue. The erosive process also taps capillaries and small blood vessels, and blood liberated in this way is likewise taken up into the trophoblast. Such engulfed blood cells and fragments of tissue are presumably utilized for nourishment in the early period of establishment of the expanding blastocystic sac. Yet these erosive activities decline in intensity by the end of the third week of development, and at this time the sac is completing the first phase of its specialization (Figure 1L-->[s]I1080.jpg[/s]).[/m]
	[m1]\ [/m]
	[m1]      Occasionally a fertilized egg fails to reach the uterus, implanting and beginning to develop elsewhere. This outcome is called an ectopic (<<ectopic pregnancy>>) or extra-uterine pregnancy. The most common ectopic site is the uterine tube—this type of pregnancy, if not treated, can be fatal for the mother—but the peritoneum lining the abdominal cavity and even the interior of the ovary are also involved, though rarely. The unsuitability of all these sites for continued development usually leads to early death and resorption of the embryo.[/m]
	[m1]\ [/m]
	[m1]{{5}}[b]Placentation[/b][/m]
	[m1][s]I1078.jpg[/s] [i][/i][s]I1079.jpg[/s] [i][/i]The irregular strands of invasive syncytial trophoblast constitute a first stage in the formation of true villi (<<villus>>) (which form part of the placenta and are briefly described below). Primitive connective tissue soon lines the interior of the blastocyst wall, and this complex (trophoblast and connective tissue) is then named the  <<chorion>>. Connective tissue promptly grows into the trophoblastic strands, and blood vessels develop in the tissue. The result is the production of many chorionic villi, each resembling a tiny, branching bush (Figure 1I-->[s]I1078.jpg[/s], Ja-->[s]I1079.jpg[/s]).[/m]
	[m1]\ [/m]
	[m1][s]I1081.jpg[/s] [i][/i]In the fourth week of development, the essential arrangements have been established that make possible those physiological exchanges between mother and fetus that characterize the remainder of pregnancy. The deepest embedded portion of the chorionic wall becomes the so-called chorionic plate of the developing  <<placenta>>. From the plate extend the main stems of chorionic villi, which give off progressively subdividing branches (Figure 1N, P-->[s]I1081.jpg[/s]). In general, the side branches are free, whereas apical ones tend to attach to the maternal tissue and serve as anchors. The villous trees occupy a labyrinthine space between the villi that was created by erosion of the uterine lining. Trophoblast not only covers the chorionic plate and its villi but also spreads like a carpet over the eroded surface of the maternal tissue.[/m]
	[m1]\ [/m]
	[m1]      Tapped uterine arteries open into the trophoblast-lined intervillous space, their blood bathing the branches and twigs of the villous trees. This blood drains from the intervillous space through similarly tapped veins. Arterial blood of the embryo, and later fetus, passes through vessels of the umbilical cord to the chorionic plate. Thence it is distributed to the villous stems, branches, and twigs through vessels in their connective-tissue cores. Return of this blood to the fetus is by a reverse route.[/m]
	[m1]\ [/m]
	[m1]      The circulation of maternal blood through the intervillous space is wholly separate from fetal blood coursing through the chorion and its villi. Communication between the two is solely by diffusive interchange. The barrier between the two circulations consists of the trophoblastic covering of villi, the connective tissue of the villous cores, and the thin lining of the capillaries that are contained in the villous cores. The placenta serves the fetus in several ways, most of which involve interchanges of materials carried in the bloodstreams of the mother and fetus. These functions are of the following kinds: (1) nutrition, (2) respiration, (3) excretion, (4) barrier action (e.g., prevention of intrusions by bacteria), and (5) synthesis of hormones and enzymes.[/m]
	[m1]\ [/m]
	[m1]{{4}}[b]Extra-embryonic membranes (<<membrane>>)[/b][/m]
	[m1]      The way in which the encapsulating membrane of the blastocyst becomes the chorion, and the most deeply embedded part of it becomes the fetal placenta, has already been described. There are still other important membranes that develop from those portions of the inner cell mass of the blastocyst that are not directly involved in becoming an embryo.[/m]
	[m1]\ [/m]
	[m1]{{5}}[b]Yolk sac[/b][/m]
	[m1][s]I1078.jpg[/s] [i][/i][s]I1079.jpg[/s] [i][/i][s]I1080.jpg[/s] [i][/i]Cells split off from the inner cell mass of the blastocyst and fashion themselves into a primitive yolk sac (Figure 1I-->[s]I1078.jpg[/s], Ja-->[s]I1079.jpg[/s]). The roof of the sac then folds into a tubular gut, whereas the remainder becomes a vascularized bag that attains the size of a small pea (Figure 1M-->[s]I1080.jpg[/s]). In other vertebrates, such as amphibians and birds, the yolk sac is large and contains a store of nutritive yolk. But in man and other true mammals there is practically none. A slender neck, the yolk stalk, soon connects the rapidly elongating gut with the fast growing yolk sac proper. The stalk detaches from the intestine early in the second month, but the shrunken sac commonly persists and can be found in the expelled afterbirth.[/m]
	[m1]\ [/m]
	[m1]{{5}}[b] <<amnion>>[/b][/m]
	[m1][s]I1078.jpg[/s] [i][/i][s]I1080.jpg[/s] [i][/i][s]I1081.jpg[/s] [i][/i][s]I1081.jpg[/s] [i][/i]A cleft separates the outermost cells of the inner cell mass of the blastocyst from the remainder, which then becomes the embryonic disk (Figure 1G-->[s]I1078.jpg[/s]). The split-off, thin upper layer is the  <<amnion>>, which remains attached to the periphery of the embryonic disk. As the disk folds into a cylindrical embryo, the amniotic margin follows the underfolding, and its line of union becomes limited to the ventral (frontward) body wall, where the umbilical cord attaches (Figure 1K-->[s]I1080.jpg[/s], O-->[s]I1081.jpg[/s]). The amnion becomes a tough, transparent, nonvascular membrane that gradually fills the chorionic sac and then fuses with it (Figure 1N, P-->[s]I1081.jpg[/s]). At the end of the third month of pregnancy, the nonplacental extent of this nearly exposed double membrane comes into contact with the lining of the uterus elsewhere. Fusion then obliterates the uterine cavity, which has been undergoing progressive reduction in size. For the remainder of pregnancy the only cavity within the uterus is that of the fluid-filled amniotic sac.[/m]
	[m1]\ [/m]
	[m1]      Clear, watery fluid fills the amniotic sac. The embryo is suspended in this fluid and thus can maintain its shape and mold its body form without hindrance. Throughout pregnancy the amniotic sac serves as a water cushion, absorbing jolts, equalizing pressures, and permitting the fetus to change posture. At childbirth it acts as a fluid wedge that helps dilate the neck of the uterus. When the sac ruptures, about a quart of fluid escapes as the “waters.” If the sac does not rupture or if it covers the head at birth, it is known as a caul.[/m]
	[m1]\ [/m]
	[m1]{{5}}[b] <<allantois>>[/b][/m]
	[m1][s]I1080.jpg[/s] [i][/i][s]I1081.jpg[/s] [i][/i]The allantois, a tube of  <<endoderm>> (the innermost germ layer), grows out of the early yolk sac in a region that soon becomes the hindgut. The tube extends into a bridge of mesoderm (the middle germ layer) that connects embryo with chorion and will become incorporated into the umbilical cord (Figure 1K-->[s]I1080.jpg[/s], O-->[s]I1081.jpg[/s]). The human allantoic tube is tiny and never becomes a large sac with important functions, as it does in reptiles, birds, and many other mammals. In the second month it ceases to grow, and it soon is obliterated. Blood vessels, however, develop early in its mesodermal sheath, and these spread into the chorion and vascularize it. Throughout pregnancy they will keep the embryo in close relationship with the mother's uterine circulation.[/m]
	[m1]\ [/m]
	[m1]{{5}}[b] <<umbilical cord>>[/b][/m]
	[m1][s]I1081.jpg[/s] [i][/i][s]I1081.jpg[/s] [i][/i][s]I1081.jpg[/s] [i][/i]As the ventral body wall closes in, the yolk stalk and allantois are brought together, along with their mesodermal sheaths and blood vessels (Figure 1K-->[s]I1081.jpg[/s], O-->[s]I1081.jpg[/s]). Enclosing everything is a wrapping of amnion. In this manner a cylindrical structure, the umbilical cord, comes to connect the embryo with the placenta (Figure 1N, P-->[s]I1081.jpg[/s]). It will serve the embryo and fetus as a physiological lifeline throughout the period of pregnancy. The mature cord is about 1.3 centimetres (0.5 inch) in diameter, and it attains an average length of nearly 50 centimetres (two feet).[/m]
	[m1]\ [/m]
	[m1]{{3}}[b][c cadetblue]Formation of the three primary germ layers[/b][/c][/m]
	[m1][s]I1078.jpg[/s] [i][/i][s]I1079.jpg[/s] [i][/i]The inner cell mass, attached to the deep pole of the implanted blastocyst, is sometimes called the embryoblast, since it supplies the materials used in the formation of an embryo. The cellular mass flattens and enters into the process of gastrulation, through which the three primary germ layers segregate and the gastrula stage, the next advance after the blastula, begins to take form. First, cells facing the cavity of the blastocyst arrange into a layer named the endoderm (Figure 1G, H-->[s]I1078.jpg[/s]). The thick residual layer, temporarily designated as epiblast, is the source of a definitive uppermost sheet, the  <<ectoderm>>, and an intermediate layer, the  <<mesoderm>>. In this second phase of gastrulation, some cells of the epiblast migrate to the midline position, then turn downward and emerge beneath as mesoderm. Such cells continue to spread laterally, right and left, between the endoderm and the residue of epiblast, which is now definitive ectoderm (Figure 1Jb-->[s]I1079.jpg[/s]).[/m]
	[m1]\ [/m]
	[m1][s]I1079.jpg[/s] [i][/i]The site where the migratory mesodermal cells leave the epiblast is an elongated, crowded seam known as the primitive streak (Figure 1Ja-->[s]I1079.jpg[/s]). Similar migrating cells produce a thick knob at one end of the primitive streak. Their continued forward movement from this so-called primitive knot produces a dense band that becomes the rodlike notochord.[/m]
	[m1]\ [/m]
	[m1]      The germ layers are not segregated sheets whose cells have predetermined, limited capacities and inflexibly fixed fates in carrying out organ-building activities. Rather, the layers represent advantageously located assembly grounds out of which the component parts of the embryo emerge normally, according to a master constructional plan that assigns different parts to definite spatial positions and local sites. Thus, although the germ layers have developmental potencies in excess of their normal developmental fates, their ordinary participation in organ forming does not deviate from a definite, standard program.[/m]
	[m1]\ [/m]
	[m1]       <<Derivatives of primary germ layers>>The derivatives of the primary germ layers can best be presented in tabular form. In naming the germ-layer origin of an organ, only the principal functional tissue is designated (Table 1 (<<Derivatives of primary germ layers>>)). In a few instances, such as the suprarenal (adrenal) glands and the teeth, a compound organ has important parts of different origin.[/m]
	[m1]\ [/m]
	[m1]{{3}}[b][c cadetblue]Growth and differentiation[/b][/c][/m]
	[m1]{{4}}[b]Growth[/b][/m]
	[m1]      Growth is an increase in size, or bulk. Cell multiplication is fundamental to an increase in bulk but does not, by itself, result in growth. It merely produces more units to participate in subsequent growing. Growth is accomplished in several ways. Most important is synthesis, by which new living matter, protoplasm, is created from available foodstuffs. Another method utilizes water uptake; a human embryo of the early weeks is nearly 98 percent water, while an adult is 70 percent fluid. A third method of growth is by intercellular deposition; cells manufacture and extrude such nonliving substances as jelly, fibres, and the ground substance of cartilage and bone. Through these activities a newborn baby is several thousand million times heavier than the zygote from which it came.[/m]
	[m1]\ [/m]
	[m1]      It is obvious that uniform growth throughout the substance of a developing organism would merely produce a steadily enlarging spherical cellular mass. Local diversities in form and proportions result from differential rates of growth that operate in different regions and at different times. The particular program of starting times and growth rates, both externally and internally in the human embryo, constitutes its characteristic growth pattern. Abnormal growth occurs occasionally, and growth may be excessive or deficient. Also, such departures may be general or local, symmetrical or asymmetrical. General  <<gigantism>> usually starts before birth, and the oversized baby continues to grow at an accelerated rate. (In some instances, the existing hereditary predisposition for gigantism may not be aroused into action until some time during childhood.) In a reverse manner, general  <<dwarfism>> may exist before birth, with the individual continuing to grow only a small amount after birth and with growth then stopping at the usual time. In another departure from the usual growth pattern, the individual may be average in size at birth and grow normally for a while, with growth then coming to a premature arrest.[/m]
	[m1]\ [/m]
	[m1]{{4}}[b]Differentiation[/b][/m]
	[m1]      In a developing organism, differentiation implies increasing structural and chemical complexity. One kind of differentiation concerns changes in gross shape and organization. Such activities, related to molding the body and its integral parts into form and pattern, comprise the processes called  <<morphogenesis>>. The processes of morphogenesis are relatively simple mechanical acts: (1) cell migration; (2) cell aggregation, forming masses, cords, and sheets; (3) localized growth or retardation, resulting, in enlargements or constrictions; (4) fusion; (5) splitting, including separation of single sheets into separate layers, formation of cavities in cell masses, and forking of cords; (6) folding, including circumscribed folds that produce inpocketings and outpocketings; (7) bending, which, like folding, results from unequal growth.[/m]
	[m1]\ [/m]
	[m1]      A second kind of differentiation refers to progressive changes occurring in the substance and structure of cells, whereby different kinds of tissues are created. These changes, and the synthetic processes underlying them, constitute  <<histogenesis>>. The zygote contains all the essential factors for development, but they exist solely as an encoded set of instructions localized in the genes of chromosomes and bearing no direct physical relationship to the future characteristics of the developing embryo. During histogenesis these instructional blueprints are decoded and transformed, through cytoplasmic syntheses, into the several types and subtypes of tissues that are the structural and functional units of organs. At first, the cells of each germ layer lack an identifiable shape and are similar in chemical composition, but an invisible type of chemical differentiation soon enters. After the elaboration of specific enzyme patterns and syntheses, certain groups of cells progressively assume distinctive characters that permit their fates to be recognized. Such early stages in definite lines of differentiation of cells are often designated by the suffix “-blast” (e.g., myoblast; neuroblast).[/m]
	[m1]\ [/m]
	[m1]      The emerging cell types are discrete entities, without intermediates; for example, a transitional form between a muscle cell and a nerve cell is never seen. Neither can different, local parts of a cell carry out different types of tissue specialization, such as nerve at one end and muscle at the other end; nor can a cell, once fully committed to a particular type of specialization, abandon it and adopt a new course.[/m]
	[m1]\ [/m]
	[m1]      Under certain conditions, differentiated cells may, however, return to a simpler state. Thus, under a changed environment, cartilage may lose its matrix, and its cells may come to resemble the more primitive tissue from which it arose. Nevertheless, despite such reversal and apparent simplification (“dedifferentiation”), these cells retain their former histological specificity. Under suitable environmental conditions they can differentiate again but can only regain their previous definitive characteristics as cartilage cells.[/m]
	[m1]\ [/m]
	[m1]      The final result of histogenesis is the production of groups of cells similar in structure and function. Each specialized group constitutes a fundamental tissue. There are four main types of such tissues: each of the three germ layers gives rise to sheetlike epithelia, which cover surfaces, line cavities, and are frequently glandular; ectoderm also forms the nervous tissues; and mesoderm also produces the muscular tissues and it differentiates into blood and the fibrous connective tissues (including two further specialized types, cartilage and bone).[/m]
	[m1]\ [/m]
	[m1]{{3}}[b][c cadetblue]Embryonic (<<embryo>>) acquisition of external form[/b][/c][/m]
	[m1]{{4}}[b]Development between the second and fourth weeks[/b][/m]
	[m1][s]I1078.jpg[/s] [i][/i]At the end of the second week, the embryonic region is a nearly circular plate within its well-embedded, differentiating chorionic sac (Figure 1H-->[s]I1078.jpg[/s]). This embryonic disk consists of two layers (epiblast and endoderm), with a third layer (mesoderm) just starting to spread between them. A hollow, dome-shaped amnion sac attaches to the margin of the upper layer of the disk, and a hollow yolk sac is similarly continuous with the lower layer. A broad cellular bridge attaches the complex to the chorion.[/m]
	[m1]\ [/m]
	[m1][s]I1079.jpg[/s] [i][/i]Early in the third week the embryonic disk has enlarged and become pear-shaped in outline, and a well-formed primitive streak occupies the midline of its caudal (hind) half, which is narrower. Cells from the epiblast are passing through the streak and spreading laterally in both directions beneath the uppermost layer, now ectoderm (Figure 1Ja, Jb-->[s]I1079.jpg[/s]). In this way the embryonic disk becomes three layered, and the gastrula stage of development comes to an end. At the middle of the week a thickening, the head process, is extending forward from a knoblike primitive knot located at the head end of the primitive streak. These linear thickenings define the median plane of the future embryo and thus divide the embryonic disk into precise right and left halves.[/m]
	[m1]\ [/m]
	[m1][s]I1080.jpg[/s] [i][/i]Toward the end of the week the disk elongates and becomes slipper-shaped in outline; a slight constriction demarcates it from the attached yolk sac. Growth has lengthened the region ahead of the now receding primitive streak. Here, in the midline, the ectoderm bears a definite gutter-like formation called the neural groove; it is the first indication of the future central nervous system. Beneath the groove the mesodermal head process presently rounds into an axial rod, the notochord, that serves as a temporary “backbone.” By the end of the third week a head fold, paired lateral body folds, and a tail fold become prominent, demarcating a somewhat cylindrical embryo from the still broadly attached yolk sac (Figure 1L-->[s]I1080.jpg[/s]). The neural folds, flanking the neural groove, converge and begin to meet midway of their lengths, thereby producing a neural tube at that level. Mesoderm, alongside the notochord, begins to subdivide into paired blocks called somites (<<somite>>), and the outlines of the somites show externally. From them, muscles and vertebrae will differentiate later. This stage, when the embryo is fashioning a neural tube, is often designated as a neurula.[/m]
	[m1]\ [/m]
	[m1]      In the fourth week the embryo goes beyond the external characteristics of vertebrates in general and becomes recognizable as a mammal. The week is marked by profound changes, during which the embryo acquires its general body plan. There is an increase in total length from about two to five millimetres, but size is quite variable among smaller specimens. Better correlated with the degree of development is the number of mesodermal somites, which attain their full number of about 42 during the fourth week. Some of the head of an early embryo arises from the embryonic disk in front of the primitive knot. But as the primitive streak shortens and its caudal retreat continues, such structures as the neural tube and notochord are added progressively in the wake of that retreat, and additional somite pairs also appear in steady succession.[/m]
	[m1]\ [/m]
	[m1][s]I1080.jpg[/s] [i][/i]The most important manoeuvre in the establishment of general body form is the transformation of the flat embryonic disk into a roughly cylindrical early embryo, which is attached to the yolk sac by a slender yolk stalk (Figure 1M-->[s]I1080.jpg[/s]). Three factors cooperate in producing this change: (1) There is more rapid expansion of both the embryonic area and the yolk sac than in the region joining the two. The enlarging embryonic area at first buckles upward and then overlaps the more slowly growing margin. Since growth is particularly rapid at the future head end and tail end, the embryo becomes elongate. (2) In conjunction with this overgrowth there is important underfolding, again most pronounced at the front and hind ends. Underfolding is produced by differential elongation in the regions of the brain and tail bud. Conspicuous is the change in the future cardiac (heart) and foregut region, which swings beneath the brain as on a hinge. (3) A certain amount of true constriction, through growth, purses all of these parts at the site of the future umbilicus.[/m]
	[m1]\ [/m]
	[m1]      Throughout the entire period when the body and its parts are being laid down, developmental advances tend to appear first at the head end and then progress tailward. For this reason, many structures that extend along the body for a distance show a gradation in development. The size advantage gained initially by the head end of the embryo is relinquished very slowly. Even in an infant the relatively large head and long arms are striking. A further tendency toward progressively graded development occurs from the middorsal line in a lateral (sideward) and then ventral (frontward) direction. All such relations are the visible expressions of stages in growth and differentiation.[/m]
	[m1]\ [/m]
	[m1][s]I1080.jpg[/s] [i][/i]Early in the fourth week the cylindrical shape of the embryo is plain, even though the folding-off process is far from complete (Figure 1M-->[s]I1080.jpg[/s]). The neural tube is still open near both ends, and at the head end the broader neural folds indicate the future brain and even its three primary divisions. A pronounced bulge beneath the brain region denotes where the  <<heart>> is forming precociously in order to institute a necessary, prompt circulation through the placenta.[/m]
	[m1]\ [/m]
	[m1]      During the middle and late days of the fourth week there are marked advances. Accelerated growth along the dorsal region bends the total body length progressively until the embryo assumes a striking C-shape, with the tips of the head and  <<tail>> not far apart. Continued growth and underfolding close in much of the ventral side of the embryo, so that a free head and upper trunk, and a lower trunk and a prominent tail, are easily recognizable. Forebrain, midbrain, and hindbrain can be identified, largely because of a sharp bend in the midbrain. Local outgrowths from each side of the forebrain produce stalked eye cups, and a pair of inpocketings of the ectoderm alongside the hindbrain sink beneath the surface as otic vesicles, forerunners of the inner ears (<<inner ear>>). Bulges indicative of the heart and liver are prominent. Formations called branchial arches, reminiscent of the gill arches of fishes and aquatic amphibians, become conspicuous in the future jaw–neck region. Paired swellings (“buds”) off the trunk foretell the locations of the upper and lower limbs.[/m]
	[m1]\ [/m]
	[m1]{{4}}[b]Developmental changes in the fifth to eighth weeks[/b][/m]
	[m1]      A five-week embryo is about eight millimetres (0.32 inch) long, whereas at six weeks the length is about 13 millimetres (0.52 inch). New external features are olfactory pits at the tips of the bent head. An umbilical cord becomes a definite entity, its proximal end occupying a low position on the abdominal wall. The sharply bent head joins the rest of the body at an acute angle. The first pair of branchial arches branch Y-fashion into maxillary and mandibular processes (primitive upper and lower jaws). The external ears are forming around the paired grooves located between each half of the mandible and each second branchial arch. The heart, which was previously the chief ventral prominence, now shares this distinction with the rapidly growing liver. Limb buds have elongated markedly and become flattened at their outer ends. A constriction on each bud separates a paddle-like hand plate or foot plate from a cylindrical segment attached to the body wall. Predictably, the upper limbs are somewhat further advanced than the lower pair.[/m]
	[m1]\ [/m]
	[m1]      In the latter weeks of the second month, developmental changes advance from those that distinguish primates to a state that is recognizably human. At the end of the second month (30 millimetres', or 1.2 inches', length) the stage of the embryo ends; henceforth, until birth, “fetus” (<<fetus>>) is the preferred term.[/m]
	[m1]\ [/m]
	[m1]      In the seventh and eighth weeks the head becomes more erect and the previously curved trunk becomes straighter. The heart and liver, which earlier dominated the shape of the ventral body, yield to a more evenly rounded chest–abdomen region. The tail, which at an earlier time was one fifth of the embryo's length, becomes inconspicuous both through actual regression and through concealment by the growing buttocks. The  <<face>> rapidly acquires a fairly human appearance; eyes, ears, and jaws are prominent. The eyes, previously located on the sides of the head, become directed forward; the  <<nose>> lacks a bridge and so is of the “pug” type, with the nostrils directed forward instead of downward. A mandibular branch of each Y-shaped branchial arch combines with its mate to form the lower  <<jaw>>. The maxillary branch on each side joins an elevation on the medial (inner) side of the corresponding nostril to produce the more complicated upper jaw. Branchial arches, other than those forming the jaws and external ears, are effaced through incorporation into an emerging, recognizable neck. Limbs become jointed, and the earlier hand plates and  <<foot>> plates differentiate terminal digits. Primitive external genitalia appear, but in a nondistinctive, sexless condition.[/m]
	[m1]\ [/m]
	[m1]      Almost all of the internal organs are well laid down at the end of eight weeks, when the embryo is little more than 25 millimetres (one inch) long. The characteristic external features are established, and subsequent growth merely modifies existing proportions without adding new structure. Similarly, the chief changes undergone by internal organs and parts are those of growth and tissue specialization. At eight weeks the neuromuscular mechanism attains a degree of perfection that permits some response to delicate stimulation.[/m]
	[m1]\ [/m]
	[m1]{{4}}[b]Development from the third month to birth[/b][/m]
	[m1][s]I1081.jpg[/s] [i][/i]During the third month the young fetus clearly resembles a human being, although the head is disproportionately large (Figure 1P-->[s]I1081.jpg[/s]). The previous protrusion of much of the intestine into the umbilical cord is reduced through the return of its loops into the abdomen. The ears rise to eye level and the eyelids (<<eyelid>>) fuse shut. Nails begin forming; ossification (bone-forming) centres appear in most of the future bones; and the sex of external genitalia becomes recognizable. (In this paragraph, and in the next two, the months are lunar months, of 28 days).[/m]
	[m1]\ [/m]
	[m1]      At four months individual differences between the faces of fetuses become distinguishable. The face is broad but the eyes are now less widely separated. The umbilical cord attaches higher on the abdominal wall; this location is above an expanding region between the cord and the pubis (front bones of the pelvis) that scarcely existed previously.[/m]
	[m1]\ [/m]
	[m1]      At five months downy hairs (lanugo) cover the body, and some head hairs appear. The skin is less transparent. Fetal movements (“quickening”) are felt by the mother. At six months eyebrows and eyelashes are clearly present. The body is lean, but its proportions have improved. The  <<skin>> is wrinkled. At seven months the fetus resembles a dried-up old person. Its reddish, wrinkled skin is smeared with a greasy substance (vernix caseosa). The eyelids reopen. At eight months fat is depositing beneath the skin. The testes begin to invade the scrotum. At nine months the dull redness of the skin fades and wrinkles smooth out. The body and limbs become better rounded.[/m]
	[m1]\ [/m]
	[m1]      At full term (38 weeks) the body is plump and proportions are improved, although the head is large and the lower limbs are still slightly shorter than the upper limbs. The skin has lost its coat of lanugo hair, but it is still smeared with vernix caseosa. Nails project beyond the finger tips and to the tips of toes. The umbilical cord now attaches to the centre of the abdomen. The testes of males are usually in the scrotum; the greater lips of the female external genitalia, which previously gaped, are now in contact. Cranial bones meet except at some angular junctions, or “soft spots.”[/m]
	[m1]\ [/m]
	[m1]       <<Age, size, and weight>>The average time of delivery (<<parturition>>) is 280 days from the beginning of the last menstrual period, whereas the duration of  <<pregnancy>> (age of the baby) is about 266 days (38 weeks; Table 2 (<<Age, size, and weight>>)). Pregnancy may extend to 300 days, or even more, in which case the baby tends to be heavier. Even when treated in a neonatal unit, premature babies born under 27 weeks of age are less likely to survive, whereas those more than 30 weeks old usually do survive.[/m]
	[m1]\ [/m]
	[m1]{{2}}[i][b][c darkcyan]Abnormal development[/b][/i][/c][/m]
	[m1]\ [/m]
	[m1]{{3}}[b][c cadetblue]Multiple births[/b][/c][/m]
	[m1]      It is both unusual and abnormal for the human species to produce more than one offspring at a time. “Twins” (<<twin>>) and “twinning” are used as general terms for any number of multiple births, as the same basic principles apply.[/m]
	[m1]\ [/m]
	[m1]      Fraternal twins stem from multiple ovulations in the same cycle. Each oocyte develops singly in a separate follicle, is shed and fertilized individually, develops within its own chorionic sac, and forms an individual placenta. In some instances, two blastocysts implant close together and the expanding placentas meet and fuse. In such double placentas, however, the two blood circulations rarely communicate. The word dizygotic technically designates two-egg twins. Such pairs obviously are independent in sex determination and bear no more resemblance than do other children of the same parents. Properly speaking, they are merely littermates. Nearly three-fourths of all American twins are dizygotic, whereas the Japanese ratio is only one-fourth. A tendency toward such multiple births exists in some family lines.[/m]
	[m1]\ [/m]
	[m1]      Wholly different are those true twins who are always of the same sex and are strikingly similar in physical, functional, and mental traits. Such close identity is enforced by their derivation from a single ovulated and fertilized egg, and hence by their acquisition of identical chromosomal constitutions. This twin type is named monozygotic. Three-fourths of such pairs develop within a common chorionic sac and share a placenta; one-fourth have individual sacs and placentas. The latter condition results from a mishap before implantation, when the cleavage cells separate into two groups and then become individually implanting blastocysts. There is no discernible hereditary tendency toward the production of monozygotic twins.[/m]
	[m1]\ [/m]
	[m1]      Several atypical processes involving the inner cell mass or embryonic plate can produce separate embryos within a single sac: (1) The inner cells of a blastocyst may segregate into two masses. (2) Somewhat later in time, two embryonic axes may become established on a single embryonic disk. (3) A single axis may subdivide by fission or budding. (4) Duplication of any sort may combine with secondary subdivision; the Dionne quintuplets are believed to have followed this sequence, which is also normal for the regular quadruplets of the Texas armadillo.[/m]
	[m1]\ [/m]
	[m1]      Occasionally monozygotic twinning can result in fused, or conjoined, twins. Conjoining results from divergent growth at the front or hind end of the emerging primitive axis of an embryo, or at both ends. The degree of union varies from slight to extensive, and the possession of a single or double set of internal organs depends on the intimacy of fusion at any particular level. Union occurs by the heads, upper trunks, or lower trunks; the joining may be by the dorsal, lateral, or ventral surfaces. Sometimes there is a marked disparity in the size of the two twins; this condition is known as nonsymmetric twinning, and usually the much smaller twin will be dependent on the larger for nutrition.[/m]
	[m1]\ [/m]
	[m1]{{3}}[b][c cadetblue]Fetal deviations (<<congenital disorder>>)[/b][/c][/m]
	[m1]      Human embryos are subject to disease, abnormal development, and abnormal growth. Decline and death can occur at any stage, but since most deaths occur in the first two or three weeks of development they usually escape notice. Probably little more than half of all zygotes reach full-term birth. Most abnormalities resulting from faulty development originate within the first seven weeks of pregnancy, before the prospective mother is aware of her condition. Abnormalities that do occur in living infants tend toward the milder types, since the severe mishaps commonly terminate development before birth.[/m]
	[m1]\ [/m]
	[m1]      Folklore (<<folk literature>>) maintains that a pregnant woman may “mark” her baby through incurring physical injuries or becoming subjected to horrors or repulsive sights. As there are no nervous connections between mother and fetus, such beliefs lack foundation. Moreover, practically all of the alleged causative experiences occur long after the “related” abnormalities have been established in the embryo.[/m]
	[m1]\ [/m]
	[m1]      Defective health of the mother can, in some instances, become a cause of the physical impairment or death of a fetus. Certain infectious diseases (<<infectious disease>>), for example, may result in fetal injury; such related causative organisms can be a virus (German measles), a spirochetal microorganism ( <<syphilis>>), or a protozoon parasite ( <<toxoplasmosis>>). Also, placental disorders, malformations of the mother's reproductive organs, and inadequate functioning of her endocrines may provide an unfavourable environment for normal development. Birth itself imposes the risk of oxygen deficiency or other injury; either may result in some malfunctioning of the brain.[/m]
	[m1]\ [/m]
	[m1]{{3}}[b][c cadetblue] <<teratology>>[/b][/c][/m]
	[m1]       <<teratology>> is concerned with all features of abnormal generation and development of the embryo (embryogenesis) and their end products. The incidence of defective development is high. One infant in 14 that survive the neonatal period bears an abnormality of some kind and degree, and half of these babies have more than one malformation. Internal, concealed defects are more numerous than external ones, and some defects do not become apparent until childhood. One baby in 40 is born with a structural defect that needs treatment. Some types of abnormality are commoner in males (e.g., pyloric stenosis, the narrowing of the opening between the stomach and the intestine), while other types predominate in females (e.g., dislocated hip). Besides obvious congenital defects, there are aberrations at the molecular level known as inborn errors of metabolism. In these an enzyme deficiency blocks the course of intermediary metabolism and results in abnormal chemical functioning. Such errors involve proteins, carbohydrates, lipids, and pigments. The abnormal products may be stored or excreted.[/m]
	[m1]\ [/m]
	[m1]      Important among causes of abnormalities are hereditary (<<heredity>>) factors. Such include gene mutations (<<mutation>>), which may become Mendelian dominants (e.g., fused fingers, which need be inherited from only one parent to appear in the offspring), recessives (e.g., albinism, which does not become evident unless its gene is inherited from both parents), or sex-linked factors (e.g., hemophilia). Besides the heritable defects, whose possibilities of recurrence can be estimated, there are many genetic results that are due to chance, are not passed on, and do not occur in other offspring. An unequal distribution of chromosomes during meiosis, leading to abnormal assortments, occurs in somatic (nonsex) chromosomes (e.g., Down's syndrome (<<Down syndrome>>)) and in sex chromosomes (e.g.,  <<Klinefelter's syndrome>>).[/m]
	[m1]\ [/m]
	[m1]      Environmental factors, both external and internal, are also important. Among physical agents, mechanical pressures or blows are no longer considered to be significant because of the protection supplied by the uterus and the fluid-filled amniotic sac. On the other hand, irradiation is a wholly effective physical agency, as experiments have amply proved. Various chemical agents, used experimentally on pregnant animals, are highly teratogenetic (productive of physical defects within the uterus), and at least one drug (thalidomide) has demonstrated its potency on human embryos. Deficiencies of some fetal hormones (<<hormone>>) are associated causally with bodily defects (e.g., male hormone and false  <<hermaphroditism>>, a condition in which the gonads are of one sex but some appearances suggest the other). Similarly, hormonal excess can be productive (e.g., growth-promoting hormone and gigantism).[/m]
	[m1]\ [/m]
	[m1]      There appear to be several ways in which teratogenetic agents can affect susceptible embryonic cells. But whatever the manner of interference may be, the final result is probably either cell impairment or death or a changed rate of growth. Either of these measures puts local development out of step with adjoining parts and upsets the coordinated schedule of development.[/m]
	[m1]\ [/m]
	[m1]{{2}}[i][b][c darkcyan]Development of organs[/b][/i][/c][/m]
	[m1]\ [/m]
	[m1]{{3}}[b][c cadetblue]Ectodermal (<<ectoderm>>) derivatives[/b][/c][/m]
	[m1]{{4}}[b]Integumentary system[/b][/m]
	[m1]      The skin has a double origin. Its superficial layer, or  <<epidermis>>, develops from ectoderm. The initial single-layered sheet of epithelial cells becomes multilayered by proliferation, and cells nearer the surface differentiate a horny substance. Pigment granules appear in the basal layer, at least, of all races. The epidermis of the palm and sole becomes thicker and more specialized than elsewhere. Cast-off superficial cells and downy hairs mingle with a greasy glandular secretion and smear the skin in the late fetal months; the pasty mass is called vernix caseosa. The deep layer of the skin, or  <<dermis>>, is a fibrous anchoring bed differentiated from mesoderm. In the later fetal months the plane of union between epidermis and dermis becomes wavy. The permanently ridged patterns are notable at the surface of the palm and sole.[/m]
	[m1]\ [/m]
	[m1]      Nails (<<nail>>) develop in pocket-like folds of the skin near the tips of digits. During the fifth month specialized horny material differentiates in proliferating ectodermal cells. The resulting nail plate is pushed forward as new plate substance is added in the fold. Fingernails reach the finger tips one month before birth. Hairs, produced only by mammals, begin forming in the third month as cylindrical pegs that grow downward from the epidermis into the dermis. Cells at the base of the peg proliferate and produce a horny, pigmented thread that moves progressively upward in the axis of the original cylinder. This first crop of hairs is a downy coat named lanugo. It is prominent by the fifth month but is mostly cast off before birth. Unlike nails, hairs are shed and replaced periodically throughout life.[/m]
	[m1]\ [/m]
	[m1]      Sebaceous glands (<<sebaceous gland>>) develop into tiny bags, each growing out from the epithelial sheath that surrounds a hair. Their cells proliferate, disintegrate, and release an oily secretion. Sweat glands at first resemble hair pegs, but the deep end of each soon coils. In the seventh month an axial cavity appears and later is continued through the epidermis. The mammary glands (<<mammary gland>>), peculiar to mammals, are specialized sweat glands. In the sixth week a thickened band of ectoderm extends between the bases of the upper and lower limb buds. In the pectoral (chest) region only, gland buds grow rootlike into the primitive connective tissue beneath. During the fifth month 15 to 20 solid cords foretell the future ducts of each gland. Until late childhood the mammary glands are identical in both sexes.[/m]
	[m1]\ [/m]
	[m1]{{4}}[b] <<mouth>> and anus[/b][/m]
	[m1]\ [/m]
	[m1]{{5}}[b]Mouth[/b][/m]
	[m1]      The mouth is a derivative of the stomodaeum, an external pit bounded by the overjutting primitive nasal region and the early upper and lower jaw projections. Its floor is a thin membrane, where ectoderm and endoderm fuse. Midway in the fourth week this membrane ruptures, making continuous the primitive ectodermal mouth and endodermal pharynx (throat). Lips and cheeks arise when ectodermal bands grow into the mesoderm and then split into two sheets. Teeth (<<tooth>>) have a compound origin: the cap of enamel develops from ectoderm, whereas the main mass of the tooth, the dentin, and the encrusting cementum about the root differentiate from mesoderm. The salivary glands (<<salivary gland>>) arise as ectodermal buds that branch, bushlike, into the deeper mesoderm. Berrylike endings become the secretory acini (small sacs), while the rest of the canalized system serves as ducts. The palate is described in relation to the nasal passages. A tiny pocket detaches from the ectodermal roof of the stomodaeum and becomes the anterior, or frontward, lobe of the hypophysis, also called the pituitary. The anterior lobe fuses with the neural lobe of the gland (see below).[/m]
	[m1]\ [/m]
	[m1]{{5}}[b] <<anus>>[/b][/m]
	[m1]      A double-layered, oval membrane separates the endodermal hindgut from an ectodermal pit, called the proctodaeum, the site of the future anal canal and its orifice, the anus. Rupture at eight weeks creates a communication between the definitive anus and the rectum.[/m]
	[m1]\ [/m]
	[m1]{{4}}[b]Central nervous system (<<nervous system, human>>)[/b][/m]
	[m1][s]I1080.jpg[/s] [i][/i]Both the brain and the spinal cord arise from an elongate thickening of the ectoderm that occupies the midline region of the embryonic disk. The sides of this neural plate elevate as neural folds, which then bound a gutter-like neural groove (Figure 1L-->[s]I1080.jpg[/s]). Further growth causes the folds to meet and fuse, thereby creating a neural tube. The many-layered wall of this tube differentiates into three concentric zones, first indicated in embryos of five weeks. The innermost zone, bordering the central canal, becomes a layer composed of long cells called ependymal cells, which are supportive in function. The middle zone becomes the gray substance, a layer characterized by nerve cells. The outermost zone becomes the white substance, a layer packed with nerve fibres. The neural tube also is demarcated internally by a pair of longitudinal grooves into dorsal and ventral halves. The dorsal half is a region associated with sensory functioning and the ventral half with motor functioning.[/m]
	[m1]\ [/m]
	[m1]      The gray substance contains primitive stem cells, many of which differentiate into neuroblasts. Each neuroblast becomes a  <<neuron>>, or a mature nerve cell, with numerous short branching processes, the dendrons, and with a single long process, the axon. The white substance lacks neuroblasts but contains closely packed axons, many with fatty sheaths that produce the whitish appearance. The primitive stem cells of the neural tube also give rise to nonnervous cells called neuroglia cells.[/m]
	[m1]\ [/m]
	[m1]{{5}}[b] <<brain>>[/b][/m]
	[m1]      The head end of the neural plate becomes expansive even as it closes into a tube. This brain region continues to surpass the spinal cord region in size. Three enlargements are prominent, the forebrain, midbrain, and hindbrain. The forebrain gives rise to two secondary expansions, the telencephalon and the diencephalon. The midbrain, which remains single, is called the mesencephalon. The hindbrain produces two secondary expansions called the metencephalon and the myelencephalon.[/m]
	[m1]\ [/m]
	[m1]      The telencephalon outpouches, right and left, into paired cerebral hemispheres, which overgrow and conceal much of the remainder of the brain before birth. Late in fetal life the surface of the  <<cerebrum>> becomes covered with folds separated by deep grooves. The superficial gray cortex is acquired by the migration of immature nerve cells, or neuroblasts, from their primary intermediate position in the neural wall. The diencephalon is preponderantly gray substance, but its roof buds off the pineal body, which is not nervous tissue, and its floor sprouts the stalk and neural (posterior) lobe of the pituitary. The mesencephalon largely retains its early tubular shape. The metencephalon develops dorsally into the imposing  <<cerebellum>>, with hemispheres that secondarily gain convolutions clothed with a gray cortex. The myelencephalon is transitional into the simpler spinal cord. Roof regions of the telencephalon, diencephalon, and myelencephalon differentiate the vascular choroid plexuses (including portions of the pia mater, or innermost brain covering, that project into the ventricles, or cavities, of the brain). The choroid plexuses secrete cerebrospinal fluid.[/m]
	[m1]\ [/m]
	[m1]{{5}}[b] <<spinal cord>>[/b][/m]
	[m1]      For a time the spinal cord portion of the neural tube tapers gradually to an ending at the tip of the spine. In the fourth month it thickens at levels where nerve plexuses, or networks, supply the upper and lower limbs; these are called the cervical and lumbosacral enlargements. At this time the spine begins to elongate faster than the spinal cord. As a result, the caudal (hind) end of the anchored cord becomes progressively stretched into a slender, nonnervous strand known as the terminal filament. Midway in the seventh month the functional spinal cord ends at a level corresponding to the midpoint of the kidneys. Both the brain and the spinal cord are covered with a fibrous covering, the dura mater, and a vascular membrane, the pia-arachnoid. These coverings differentiate from local, neighbouring mesoderm.[/m]
	[m1]\ [/m]
	[m1]{{4}}[b]Peripheral nervous system[/b][/m]
	[m1]      In general, each craniospinal nerve has a dorsal (posterior) root that bears a  <<ganglion>> (mass of nerve tissue) containing sensory nerve cells and their fibres, and a ventral (anterior) root that contains motor nerve fibres but no nerve cells. Ganglion cells differentiate from cells of the neural crest, which is at first a cellular band pinched off from the region where each neural fold continues into ordinary ectoderm. Each of these paired bands breaks up into a series of lumps, spaced in agreement with the segmentally arranged mesodermal somites. Neuroblasts within these primordial ganglia develop a single stem and hence are called unipolar. From this common stem one nerve process, or projection, grows back into the adjacent sensory half of the neural tube; another projection grows in the opposite direction, helping to complete the dorsal root of a nerve. Neuroblasts of motor neurons arise in the ventral half of the gray substance of the neural tube. They sprout numerous short, freely branching projections, the dendrons, and one long, little-branching projection, the axon; (<<axon>>) such a neuron is called multipolar. These motor fibres grow out of the neural tube and constitute a ventral root. As early as the fifth week they are joined by sensory fibres of the dorsal root and continue as a nerve trunk.[/m]
	[m1]\ [/m]
	[m1]      Cells of the  <<neural crest>> differentiate into things other than sensory neurons. Among these variants are cells that encapsulate ganglion cells and others that become neurolemma cells, which follow the peripherally growing nerve fibres and ensheath them. The neurolemma cells cover some nerve fibres with a fatty substance called myelin.[/m]
	[m1]\ [/m]
	[m1]{{5}}[b]Spinal nerves (<<spinal nerve>>)[/b][/m]
	[m1]      Spinal nerves are sensorimotor nerves, with dorsal and ventral roots. A network called a brachial plexus arises in relation to each upper limb and a lumbosacral plexus in relation to each lower limb. The spine, elongating faster than the spinal cord, drags nerve roots downward, since each nerve must continue to emerge between the same two vertebrae. Because of their appearance, the obliquely coursing nerve roots are named the cauda equina, the Latin term for horse's tail.[/m]
	[m1]\ [/m]
	[m1]{{5}}[b]Cranial nerves (<<cranial nerve>>)[/b][/m]
	[m1]      Cranial nerves V, VII, IX, and X arise in relation to embryonic branchial arches but have origins similar to the spinal nerves. The olfactory nerves (cranial nerve I) are unique in that their cell bodies lie in the olfactory epithelium (the surface membrane lining the upper parts of the nasal passages), each sending a nerve fibre back to the brain. The so-called optic nerves (<<optic nerve>>) (II) are not true nerves but only tracts that connect the retina (a dislocated portion of the brain) with the brain proper. Nerves III, IV, VI, and XII are pure motor nerves that correspond to the ventral roots of spinal nerves. The acoustic nerves (<<vestibulocochlear nerve>>) (VIII) are pure sensory nerves, each with a ganglion that subdivides for auditory functions and functions having to do with equilibrium and posture; they correspond to dorsal roots. Nerves X and XI are a composite of which XI is a motor component.[/m]
	[m1]\ [/m]
	[m1]{{4}}[b] <<autonomic nervous system>>[/b][/m]
	[m1]      The autonomic nervous system is made up of two divisions, the sympathetic and the parasympathetic nervous systems; it controls such involuntary actions as constriction of blood vessels. Some cells of the neural crests migrate and form paired segmental masses alongside the aorta, a principal blood vessel. Part of the cells become efferent multipolar ganglion cells (cells whose fibres carry impulses outward from ganglions, or aggregates of nerve cells) and others merely encapsulate the ganglion cells. These autonomic ganglia link into longitudinal sympathetic trunks. Some of the neuroblasts migrate farther and assemble as collateral ganglia—ganglia not linked into longitudinal trunks. Still others migrate near, or within, the visceral organs that they will innervate and produce terminal ganglia. These ganglia are characteristic of the parasympathetic system.[/m]
	[m1]\ [/m]
	[m1]      Some cells of certain primitive collateral ganglia leave and invade the amassing mesodermal cortex of each adrenal gland. Consolidating in the centre, they become the endocrine cells of the medulla.[/m]
	[m1]\ [/m]
	[m1]{{4}}[b]Sense (<<sensory reception, human>>) organs[/b][/m]
	[m1]\ [/m]
	[m1]{{5}}[b]Olfactory organ (<<smell>>)[/b][/m]
	[m1]      Paired thickenings of ectoderm near the tip of the head infold and produce olfactory pits. These expand into sacs in which only a relatively small area becomes olfactory in function. Some epithelial cells in these regions remain as inert supporting elements. Others become spindle-shaped olfactory cells. One end of each olfactory cell projects receptive olfactory hairs beyond the free surface of the epithelium. From the other end a nerve fibre grows back and makes a connection within the brain.[/m]
	[m1]\ [/m]
	[m1]{{5}}[b]Gustatory organ[/b][/m]
	[m1]      Most  <<taste>> buds arise on the  <<tongue>>. Each bud, a barrel-shaped specialization within the epithelium that clothes certain lingual papillae (small projections on the tongue), is a cluster of tall cells, some of which have differentiated into taste cells whose free ends bear receptive gustatory hairs. Sensory nerve fibres end at the surface of such cells. Other tall cells are presumably inertly supportive in function.[/m]
	[m1]\ [/m]
	[m1]{{5}}[b]Eye (<<eyeball>>)[/b][/m]
	[m1]      The earliest indication of an eye is an optic vesicle (sac) bulging from each side of the forebrain. It quickly becomes an indented optic cup, connected to the brain by a slender optic stalk. Most of the cup will become the  <<retina>>, but its rim represents the epithelial part of the insensitive ciliary body and iris. The thicker inner layer of the cup becomes the neural layer of the retina, and by the sixth month three strata of neurons are recognizable in it: (1) visual cells, each bearing either a photoreceptive rod or a cone at one end; (2) bipolar cells, intermediate in position; (3) ganglion cells, which sprout axons that grow back through the optic stalk and make connections within the brain. The thin outer layer of the cup remains a simple epithelium whose cells gain pigment and make up the pigment epithelium of the retina.[/m]
	[m1]\ [/m]
	[m1]      The lens arises as a thickening of the ectoderm adjacent to the optic cup. It inpockets to form a lens vesicle and then detaches. The cells of its back wall become tall, transparent lens fibres. Mesoderm surrounding the optic cup specializes into two accessory coats. The outer coat, the tough, white sclera, is continuous with the transparent cornea. The inner coat, the vascular choroid, continues as the vascular and muscular ciliary body and the vascularized tissue of the iris. The eyelids are folds of adjacent skin; from the inside of each upper lid several lacrimal glands bud out.[/m]
	[m1]\ [/m]
	[m1]{{5}}[b]Ear (<<hearing>>)[/b][/m]
	[m1]      The projecting part (auricle) of the external ear develops from hillocks on the first and second branchial arches. The ectodermal groove between those arches deepens and becomes the external auditory canal. The auditory tube and tympanic cavity—the cavity at the inner side of the eardrum (<<tympanic membrane>>)—are expansions of the endodermal pouch located between the first and second branchial arches. The area where ectodermal groove and endodermal pouch come in contact is the site of the future eardrum. The chain of three auditory ossicles (<<ear bone>>) (small bones) that stretches across the tympanic cavity is a derivative of the first and second arches.[/m]
	[m1]\ [/m]
	[m1]      The epithelium of the internal ear is at first a thickening of ectoderm at a level midway of the hindbrain. This plate inpockets and pinches off as a closed sac, the otocyst. Its ventral part elongates and coils to resemble a snail's shell, thereby forming the cochlear duct, or seat of the organ of hearing. A middle region of the otocyst becomes chambers known as the utricle and saccule, related to the sense of balance. The dorsal part of the otocyst remodels drastically into three semicircular ducts, related to the sense of rotation. Fibres of the acoustic nerve grow among specialized receptive cells differentiated in certain regions of these three divisions.[/m]
	[m1]\ [/m]
	[m1]{{3}}[b][c cadetblue]Mesodermal derivatives[/b][/c][/m]
	[m1]{{4}}[b]Skeletal system (<<skeletal system, human>>)[/b][/m]
	[m1]      Except for part of the skull, all bones pass through three stages of development: membranous, cartilaginous, and osseous. The earliest ossification centres appear in the eighth week, but some do not arise until childhood years and even into adolescence.[/m]
	[m1]\ [/m]
	[m1]{{5}}[b]Axial skeleton[/b][/m]
	[m1]      The ventromedial walls (the walls toward the front and the midline) of the paired somites break down, and their cells migrate toward the axial notochord and surround it. Differentiation and growth of these segmental masses produce the jointed vertebrae. Ribs (<<rib>>) also grow out of each primitive vertebral mass, but they become long only in the thoracic region. Here their ventral ends join the sternum, which arises independently by the fusion of a pair of bars.[/m]
	[m1]\ [/m]
	[m1]      The  <<skull>> has three components, different in origin. Its basal region is an ancient heritage whose bones pass through the three typical stages of development. By contrast, the sides and roof of the skull develop directly from membranous primordia, or rudiments. The jaws are derivatives of the first pair of cartilaginous branchial arches but develop as membrane bone. Ventral ends of the second to fifth arches contribute the cartilages of the larynx and the hyoid bone (a bone of horseshoe shape at the base of the tongue). Dorsal ends of the first and second arches become the three auditory ossicles (the small bones in the middle ear).[/m]
	[m1]\ [/m]
	[m1]{{5}}[b]Appendicular skeleton[/b][/m]
	[m1]      The limb bones develop in three stages from axial condensations in the local mesoderm. The shoulder and pelvic supports are comparable sets, as are the bones of the arms and legs.[/m]
	[m1]\ [/m]
	[m1]{{5}}[b]Articulations[/b][/m]
	[m1]      Some type of  <<joint>> exists wherever bones meet. Joints that allow little or no movement consist of connective tissue, cartilage, or bone. Movable joints arise as fluid-filled clefts in mesoderm, which condenses peripherally into a fibrous capsule.[/m]
	[m1]\ [/m]
	[m1]{{4}}[b]Muscular system[/b][/m]
	[m1]      Much of each somite differentiates into myoblasts (primitive muscle (<<muscle system, human>>) cells) that become voluntary muscle fibres. Aggregations of such fibres become muscles of the neck and trunk. Muscles of the head and some of the neck muscles originate from mesoderm of branchial arches. Muscles of the limbs seemingly arise directly from local mesoderm. In general, muscle primordia may fuse into composites, split into subdivisions, or migrate away from their sites of origin. During these changes they retain their original nerve supply. Regardless of differences in source of origin, all voluntary muscle fibres are of the same striated type (marked by dark and light stripes). Spontaneous movements begin to occur in embryos about 10 weeks old. In general, involuntary muscle differentiates from mesoderm surrounding hollow organs; only the cardiac muscle type is striated.[/m]
	[m1]\ [/m]
	[m1]{{4}}[b]Vascular system (<<human cardiovascular system>>)[/b][/m]
	[m1]      All hollow organs, including arteries, veins, and lymphatics, are lined with epithelium—the principal functional tissue—and are ensheathed with muscular and fibrous coats.[/m]
	[m1]\ [/m]
	[m1]{{5}}[b]Blood vessels (<<blood vessel>>)[/b][/m]
	[m1]      Primitive blood vessels arise in the mesoderm as tiny clefts bordered by flat endothelial cells. Growth and coalescence produce networks, out of which favoured channels persist as definite vessels, while others decline and disappear. A bilaterally symmetrical system of vessels is well represented in embryos four weeks old. This early plan is profoundly altered and made somewhat asymmetrical during the second month by fusions, atrophies, emergence of new vessels, and rerouting of older ones. The alterations reflect adjustments to changing form and pattern within the developing organ systems.[/m]
	[m1]\ [/m]
	[m1]      Arteries cranial to the heart (headward of the heart) are mostly products of the paired aortic arches, which course axially within the branchial arches, thus interconnecting the ventral  <<aorta>> with paired dorsal aortas. The third pair of aortic arches becomes the common carotids; the fourth pair, the aortic arch and brachiocephalic; the fifth pair, the pulmonary arteries and ductus arteriosus. The dorsal aortas fuse into the single descending aorta, which bears three sets of paired, segmental branches. The dorsal set becomes the subclavian, intercostal, and lumbar arteries. The lateral set becomes arteries to the diaphragm, the adrenal glands, the kidneys, and the sex glands. The ventral set becomes the celiac, mesenteric, and umbilical arteries. Axial arteries to both sets of limb buds emerge from an original plexus, but they undergo drastic alteration and extensive replacement.[/m]
	[m1]\ [/m]
	[m1]      The primitive veins are symmetrically bilateral. They consist of vitelline veins from the yolk sac, umbilical veins from the placenta, and precardinal and postcardinal veins from the cranial and caudal regions (the regions toward the head and toward the tail) of the body. Drastic transformations occur in all of these, and new pairs of veins (subcardinals and supracardinals) arise also, caudal to the heart. From the vitellines come chiefly the portal and hepatic veins. The left umbilical becomes the main return from the placenta by making a diagonal channel, the ductus venosus, through the liver to the heart. The precardinal veins change their names to the internal jugulars, but near the heart an interconnection permits both to drain into a common stem, then called the superior vena cava. Caudal to the heart, the postcardinals virtually disappear, and all blood return shifts to the right side as a new compound vessel, the inferior vena cava, becomes dominant. Pulmonary veins open into the left atrium. Veins from the limb buds organize from an early peripheral border vein.[/m]
	[m1]\ [/m]
	[m1]{{5}}[b]Lymphatic (<<lymphatic system>>) vessels[/b][/m]
	[m1]      The lymph vessels develop independently in close association with veins. Linkages produce the thoracic duct, which is the main drainage return for lymph. Masses of lymphocytes accumulate about lymphatic vessels and organize as lymph nodes. The spleen has somewhat similar tissue, but its channels are supplied with blood.[/m]
	[m1]\ [/m]
	[m1]{{5}}[b] <<heart>>[/b][/m]
	[m1][s]I1080.jpg[/s] [i][/i]Fusion combines two endothelial tubes, and these are surrounded by a mantle of mesoderm that will become the muscular and fibrous coats of the heart. At three weeks the heart is a straight tube that is beginning to beat (Figure 1M-->[s]I1080.jpg[/s]). Starting at the head end, four regions can be recognized: bulbus, ventricle, atrium, and sinus venosus. Since the heart is anchored at both ends, rapid elongation forces it to bend. In doing this, the sinus–atrium and bulbus–ventricle reverse their original relations. Further development concerns the transformation of a single-chambered heart into one with four chambers.[/m]
	[m1]\ [/m]
	[m1]      The  <<atrium>> becomes subdivided by the growth of two incomplete partitions, or septa, placed close together and each covering the defect in the other. The  <<ventricle>> also subdivides, but by a single, complete partition. A canal, connecting atria and ventricles, becomes two canals. The bulbus is absorbed into the right ventricle, and its continuation (the truncus) subdivides lengthwise, forming the aorta and the pulmonary artery. The right horn of the sinus venosus is absorbed into the right atrium, together with the superior and inferior venae cavae, which originally drained into the sinus. The transverse portion of the sinus persists as the coronary sinus. The pulmonary veins retain their early drainage into the left atrium. Important valves develop and ensure flow within the heart from atria to ventricles, and outward from the ventricles into the aorta and the pulmonary artery.[/m]
	[m1]\ [/m]
	[m1]      Birth initiates breathing, and the abandonment of the placental circulation follows. These changes entail a drastic rerouting of blood through the heart. As a result, the two atrial septa fuse and no longer permit blood to pass from the right atrium to the left atrium. Blood in the pulmonary artery no longer virtually bypasses the lungs; previously it had passed to the aorta directly through a shunt offered by the ductus arteriosus. As a sequel to these changes, the abandoned umbilical arteries, umbilical vein, ductus venosus, and ductus arteriosus all collapse and become fibrous cords.[/m]
	[m1]\ [/m]
	[m1]{{4}}[b]Urinary system (<<renal system>>)[/b][/m]
	[m1]      Vertebrates have made three experiments in  <<kidney>> production: the  <<pronephros>>, or earliest type; the  <<mesonephros>>, or intermediate kidney; and the  <<metanephros>>, or permanent kidney. All arise from the cellular plates called nephrotomes that connect somites with the mesodermal sheets that bound the body cavity. The vestigial pronephros is represented solely by several pairs of tubules; they join separately formed excretory ducts that grow downward and enter the  <<cloaca>>, the common outlet for urine, genital products, and for intestinal wastes. Next tailward arise some 40 pairs of nephric (kidney) tubules that constitute the mesonephros; these tubules join the same excretory ducts, hereafter called the mesonephric ducts. The two sets of mesonephric tubules serve as functioning kidneys until the 10th week.[/m]
	[m1]\ [/m]
	[m1]      Each permanent kidney, or metanephros, develops still farther tailward. A so-called ureteric primordium buds off each mesonephric duct, near its hind end. The ureteric stem elongates and expands terminally, thereby forming the renal pelvis and calices; continued bushlike branching produces collecting ducts. The early ureteric bud invades a mass of nephrotome tissue. The branching collecting ducts progressively break this tissue up into tiny lumps, each of which becomes a long secretory tubule, or  <<nephron>>, and joins a nearby terminal twig of the duct system. Continued proliferation of ducts and nephric tissue produces over a million urine-producing tubules in each kidney.[/m]
	[m1]\ [/m]
	[m1]      The blind caudal end of the endodermal hindgut absorbs the stem of each mesonephric duct, whereupon the remainder of the duct and the ureter acquire separate openings into the hindgut. This expanded region of the gut, now a potential receptacle for feces, urine, and reproductive products, is known as a cloaca. It next subdivides into a rectum behind and a urogenital sinus in front. The sinus, in turn, will specialize into the  <<urinary bladder>> and the urethra. The  <<prostate gland>> develops as multiple buds from the urethra, close to the bladder.[/m]
	[m1]\ [/m]
	[m1]{{4}}[b]Genital system[/b][/m]
	[m1]      The genital organs begin to develop in the second month, but for a time sex is not grossly distinguishable. Also, a double set of male and female ducts arise, and not until later does the unneeded set decline. Hence, this period is commonly called the indifferent stage.[/m]
	[m1]\ [/m]
	[m1]{{5}}[b]Gonads (<<gonad>>)[/b][/m]
	[m1]      Sex glands develop in a pair of longitudinal ridges located alongside the mesentery, the anchoring fold of membrane to the gut. The primordial sex cells appear first in the cloacal wall, from which they migrate upward in the gut, pass through its mesentery, and finally invade the genital ridges, where they proliferate. The testes are the earliest type of gonad to organize. They begin by developing  <<testis>> cords and a testis capsule. The cords radiate from one focal point at the periphery, and thin fibrous partitions segregate groups of the cords within wedge-shaped compartments. These cords do not gain channels and become semen-producing tubules until near the time of puberty. The ovaries (<<ovary>>) organize somewhat tardily by differentiating an outer portion, the cortex, and a central portion, the medulla. The cortex contains the primordial sex cells; these become surrounded by a layer of ordinary cells, thereby forming primary ovarian follicles. Both the testes and the ovaries undergo relative shifts from their early sites to lower positions in the body. But only the testes make a bodily descent; this is into the scrotum.[/m]
	[m1]\ [/m]
	[m1]{{5}}[b]Genital ducts[/b][/m]
	[m1]      In the male, a few mesonephric tubules on each side do not degenerate but link up with the neighbouring testis tubules. The converted mesonephric tubules and the retained mesonephric ducts become the male sex ducts. Near their terminations they outpouch seminal vesicles and then open into the urethra. In the female, a pair of ducts develops from the epithelium clothing the mesonephric ridges. These ducts, known as the uterine tubes, mostly parallel the courses of the mesonephric ducts, but at their lower ends they unite into a common tube that becomes the uterus and vagina.[/m]
	[m1]\ [/m]
	[m1]{{5}}[b]External genitalia[/b][/m]
	[m1]      Both sexes develop a genital tubercle (i.e., a knob) and a pair of urogenital folds flanked by a pair of genital swellings. At three months these rudiments begin to assume male or female characteristics. In the male, the tubercle and the united urogenital folds combine as the  <<penis>>, thereby continuing the urethra to its end; the genital swellings shift toward the anus, fuse, and become the  <<scrotum>>. In the female, the tubercle remains small, as the clitoris; (<<clitoris>>) it does not contain the urethra. The urogenital folds remain unclosed as the lesser vulvar lips and are flanked by the unshifted and unfused genital swellings, or greater lips.[/m]
	[m1]\ [/m]
	[m1]{{4}}[b]Coelom[/b][/m]
	[m1]      The lateral mesoderm, beyond the somites and nephrotomes, splits into two layers: the somatic layer and, underlying the somatic layer, the splanchnic layer. The intervening space is the coelom. As the embryo's body folds off, its coelom becomes a single closed cavity. In it can be recognized, regionally, a provisional pericardial cavity (cavity for the heart), two pleural canals (for the lungs), and a peritoneal cavity (for the abdominal contents). A thick plate of mesoderm, the transverse septum, constitutes a partial partition just ahead of the developing liver. Two pairs of membranes grow out from the septum. One set separates the pericardial cavity from the two pleural cavities; these membranes later expand into the pericardium and enclose the heart. The other pair of membranes separates the pleural cavities from the peritoneal cavity of the abdomen. The definitive  <<diaphragm>> is a composite partition, much of which is furnished by the transverse septum; lesser contributions are from the lateral body walls and the paired membranes that separated the pleural and peritoneal cavities.[/m]
	[m1]\ [/m]
	[m1]{{3}}[b][c cadetblue]Endodermal derivatives[/b][/c][/m]
	[m1]{{4}}[b] <<pharynx>>[/b][/m]
	[m1]      The tongue is a product of four branchial arches, whose ventral ends merge in its midplane. Papillae elevate from the surface, and taste buds arise as specializations within the covering epithelium of some of them. Pharyngeal pouches are early lateral expansions of the local endoderm, alternating with the branchial arches. The first pair elongate as the auditory tubes and tympanic cavities. The second pair mark the site of the tonsils. The third pair give rise to the halves of the thymus, and the third and fourth pairs produce the two sets of parathyroid glands. The  <<thyroid gland>> buds off the pharyngeal floor in the midplane and at the level of the second branchial arches.[/m]
	[m1]\ [/m]
	[m1]{{4}}[b]Digestive (<<digestive system, human>>) tube[/b][/m]
	[m1][s]I1081.jpg[/s] [i][/i]As the embryo folds off, the endoderm is rolled in as the foregut and hindgut. Continued growth progressively closes both the midbody and the midgut (Figure 1O-->[s]I1081.jpg[/s]). The  <<esophagus>> remains as a simple, straight tube. The  <<stomach>> grows faster on its dorsal side, thereby forming the bulging greater curvature; the stomach also rotates 90° so that its original dorsal and ventral borders come to lie left and right. The  <<intestine>> elongates faster than the trunk, so that its loops find temporary room by pushing into the umbilical cord. Later, the loops return, completing a rotation that gives the characteristic final placement of the small and large intestines.[/m]
	[m1]\ [/m]
	[m1]      When the gut folds into a tube it is suspended by a sheetlike dorsal  <<mesentery>>, or membranous fold. In the region of the stomach it forms an expansive pouch, the omental bursa. Secondary fusions of the bursa and of some of the rest of the mesentery with the body wall produce lines of attachment from stomach to rectum inclusive, different from the original midplane course. Such fusions also anchor firmly some parts of the tract. A ventral mesentery, beneath the gut, exists only in the region of the stomach and  <<liver>>.[/m]
	[m1]\ [/m]
	[m1]{{4}}[b]Major glands[/b][/m]
	[m1]      The liver arises as a ventral outgrowth of the foregut that invades the early transverse septum. Although rapid growth causes it to bulge prominently away from this septum, it remains attached to the septum and hence to the definitive diaphragm. The differentiating glandular tissue takes the form of plates bathed by blood channels. The stem of the original liver bud becomes the common bile duct, whereas a secondary outgrowth produces the cystic duct and the gallbladder.[/m]
	[m1]\ [/m]
	[m1]      The pancreas takes its origin from a larger dorsal bud and a smaller ventral bud, both off the foregut. The two merge and their ducts communicate, but in man it is the lesser, ventral duct that becomes the stem outlet. Secretory acini are berrylike endings of the branching ducts. Pancreatic islets arise as special sprouts from the ducts; these differentiate into endocrine tissue that secretes insulin.[/m]
	[m1]\ [/m]
	[m1]{{4}}[b]Respiratory system (<<respiration, human>>)[/b][/m]
	[m1]\ [/m]
	[m1]{{5}}[b]Nasal cavity[/b][/m]
	[m1]      The first part of the respiratory system is ectodermal in origin. The olfactory sacs (see above Ectodermal derivatives: Sense organs (<<human embryology>>)) become continuous secondarily with a passage captured from the primitive mouth cavity. This addition is produced by a horizontal partition, the palate. It arises from a pair of shelflike folds that grow out from the halves of the primitive upper jaw and then unite. The final nasal passage extends from the nostrils to the back of the pharynx.[/m]
	[m1]\ [/m]
	[m1]{{5}}[b]Larynx, trachea, and lungs[/b][/m]
	[m1]      A hollow lung bud grows off the floor of the endodermal pharynx, just caudal (tailward) to the pharyngeal pouches and in the midline. It has the form of a tube with an expanded end. The entrance to this tube is the  <<glottis>>, and the region about it becomes the  <<larynx>>. The tube proper represents the trachea. Its terminal expansion divides into two branches, and these tubes elongate as the primary bronchi. Continued growth and budding produce two side branches from the right bronchus and one from the left. These branches and the blind ends of the two parent bronchi indicate the future plan of the lungs, with three right lobes and two left lobes. Continued branchings, through the sixth month, produce bronchioles of different orders. In the final months the smaller ducts and early respiratory alveoli (air sacs) appear, the lungs losing their previous glandular appearance and also becoming highly vascular. Until breathing distends the lungs, these organs remain relatively small.[/m]
	[m1]\ [/m]
	[m1][c dimgray][i]Leslie B. Arey[/i][/c]  [b]Ed.[/b][/m]
	[m1]\ [/m]
	[m1]{{2}}[i][b][c darkcyan]Additional Reading[/b][/i][/c][/m]
	[m1] Overviews of the topic are provided by Manuel Hernández (M. Hernández Rodríguez) and Jesús Argente (eds.), [i]Human Growth: Basic and Clinical Aspects[/i] (1992), conference proceedings; Frank Falkner and J.M. Tanner (eds.), [i]Human Growth[/i], 3 vol. (1978–79), with vol. 1 and 3 in a 2nd ed. (1986); and Esmail Meisami and Paola S. Timiras (eds.), [i]Handbook of Human Growth and Developmental Biology[/i], 3 vol. in 7 (1988–90).Explorations of human embryology in particular include Stephen G. Gilbert, [i]Pictorial Human Embryology[/i] (1989), an atlas containing colourful line illustrations of histological sections and stages of human embryos, for the health professional and the general reader; Keith L. Moore and T.V.N. Persaud, [i]The Developing Human: Clinically Oriented Embryology[/i], 5th ed. (1993); Jan Langman, [i]Langman's Medical Embryology[/i], 7th ed. by T.W. Sadler (1995), a concise presentation; and Jan S. Zagon and Theodore A. Slotkin (eds.), [i]Maternal Substance Abuse and the Developing Nervous System[/i] (1992), describing the effects of maternal drug consumption on fetal, neonatal infant, and subsequent adult nervous systems.[/m]
	
trivial card
	It's a duplicate key
