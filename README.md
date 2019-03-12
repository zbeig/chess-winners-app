# chess-winners-app
A simple desktop app for generating winners of a chess tournament

Pre-requisites
==============
1. Only excel sheet to be uploaded
2. Excel document should have sheets 'sort' and 'Ranklist'
3. 'sort' sheet should have 'Prize' and 'Prize Money' columns
4. 'Ranklist' sheet should have columns in the same order as below [column name spelling doesn't
 	matter] :
	Rank-SNo.-Title-Name-Gender-IRtg-Club-Age-Type-Gr(disabilities)-Pts
5. categories related to clubs, types and disabilities(Gr) in the 'sort' sheet should match exactly with the 'club', 'type' and 'Gr' columns
   in the 'Ranklist' sheet
   e.g., if F07 is in sort sheet, then the Type column in Ranklist should also have F07 and not F7
   e.g., if PC is in sort sheet, then the Gr column in Ranklist should also have PC or VC
6. For Female "Above <age>" categories, the category name should be just 'W' [without single quotes] in sort sheet.
   The program will check for the gender column having 'F' and then for an empty 'Type' column to arrive at the winners in this category.
   e.g., W
7. For categories "Below_<rating>", make sure to append "Below_", else program will not give proper results
   e.g., Below_1800, Below_2000
8. For Unrated category, just have "Un". The zero rated players are only considered for this category
9. The Rank and the Rating columns should have some numeric value. Do not leave them blank.
10. For Club+Gender [GOA_F, GOA_M etc.,] category, append _M or _F to the Club name [exactly as in Ranklist] in the sort sheet
    e.g., when Club name is AP in Ranklist, then in sort sheet add as below
	AP_M for Male AP category prizes and AP_F for Female AP category prizes
11. For Club+Gender+Age [e.g., GOA_F07, GOA_U15 etc.,], system will match exact club name 
    and then match the type column for "U<digits>" cases
	For Female category, system will match exact club name and gender as 'F' in gender column
	and then match only the age from the type column
   
How to use the software
=======================
1. Copy the supplied zip file and extract it to a location of your choice
2. Double click on the ChessWinners.exe which is in  \ChessWinners\ folder
3. Follow the On-Screen instructions
4. The output files are located in \ChessWinners\Results folder

How to use the Java program
===========================
1. Copy the supplied jar file in a location of your choice
2. Open command prompt from that location
3. Run the below command -
   java -jar chesswinners-1.0.jar
4. You will be shown the license information. Please read, understand and the ACCEPT to use the program.
5. After acceptance, you will see "Please enter the path of input file [.xlsx only]:"
6. Enter the full path of the input file. e.g., 
   C:\Users\609239429\desktop\chessresults\NITHM-TEST.xlsx
7. You should see some logs getting printed and finally as below -
   Output File generated : WinnerList_06-08-2018_11_45_10.xlsx
8. Output file is generated in the same folder where the jar file is located
