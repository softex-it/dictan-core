#!/usr/bin/perl -w
#
# This script will remove img tags from any SOURCE file.
#
# To remove images from any FDB/ZD base:
#
# 1. Convert the base to SOURCE
# 2. Run this script at articles.txt as remove-article-images.pl
# 3. After the script is done, remove the old articles.txt, and rename articles-new.txt to articles.txt
# 4. Convert the base back from SOURCE to FDB or ZD
#

open(my $fin, '<', "articles.txt");
open(my $fout, '>', 'articles-new.txt');

while (<$fin>) {
    my @article = split('  ', $_);
    my $art_length = scalar(@article);
    if ($art_length > 1) {
        process_entry(@article);
    }
}

close $fin;
close $fout;

sub process_entry {
	
	my @entry = @_;
	
	$title = $entry[0];
	$body = $entry[1];
	
	# Remove all img tags
	$body =~ s/<img(.*?)>/ /g;
	
	print $fout "$title  $body";
	
}
