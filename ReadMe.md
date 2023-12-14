# Reflection: 
Best project we've done so far! Lots of free reign and good thinking.
## Fair warning:

A possible misunderstanding I must clear, I realize, is that the purpose of using the Djakstra algorithm for this kind of application is to find a set of conditions/outcomes that are particularily likely/unlikely to happen, not to find a set of conditions/outcomes that necessarily has already happened and was very likely/unlikely. **In other words, the final path the algorithm computes may not be a path that actually exists. Use this for prediction, not for computing existing outcomes** (For example, shortest path from starting node 7-Austin for a graph with percentage weights doesn't actually exist in the dataset) The reason for that is at any given "level" of nodes, not every node in said level is connected to the next level. Such a fact isn't harmful in this case because results where such paths do not exist happen for location source nodes that had pariticularily few cases for a certain intervention reason. Therefore, this program can be used to predict the most/least likley outcome at a given checkpoint (like an intervention reason) based upon generalizations from other source nodes, in the case of lacking data. 

This project is meant to explore the potential for using programs like this for analyses in more robust bipartite systems, where every node points to the next, and also for prediction/generalization in more limited datsets like this one. In the case that many links are undocumented and datasets are incomplete, one wants to find out that given this next checkpoint from a poorly documented source node, what can likely happen? Even if a path may not exist, this algorithm is meant to be used to get a glimpse of what set of condtiions are particularily likely/unlikely to happen from a starting point, and if one would like, they can simply heed the second node along the outputted path (like an immediate outcome that's directly connected to source node, and therefore has record in the dataset) and rerun the algorithm from that second node to see the remaining set of conditions that are generally likely/unlikely to happen.

## File overview:

Main.java: just run it, and it asks you some questions
data_simple.csv: dataset that's fed into as input for Main.java
wrangle.qmd: original r wrangling of dataset to get a more summarized form of data_simple.csv
rest of files basic overview:
Longest.java: edges are integers, Dijakstra for longest path
Shortest.java:  edges are integers, Dijakstra for shortest path
ShortestbyPercentage.jav: edges are doubles, Dijakstra for longest/shortest path depending on whether you invert percentage weights

## Thinking Process Journal: 

12/3/2023
Dear Diary,
Oh, I am dreary. Someone brought up a horrendous question today: "If you're the one waiting for food at a restaurant, aren't you the waiter?"
I counteracted that statement by stating that it's like saying if you're the one sweating in a sweater, aren't you the sweater. Instead of realizing how wrong that statement was, they nodded in contemplation. Facts can be misinterpreted and twisted both intentionally and unintentionally in this world we live in. It sounds wrong to refer to a human being as a sweater, a nonliving thing, but we no longer attach as much importance to human lives now that data is so cheap and accessible, and our lives become mere statistics. 
I need to be strong and make a meaningful project with accurate analysis for this assignment. It is my duty... 

12/6/2023 ***
Dear Diary,
I'm proud to say I started.
The dataset is sourced from this link (https://catalog.data.gov/dataset/cci-4-4-residential-child-care-investigations-rcci-priority-and-response-time-fy2013-2022) and I wrangled it (check wrangle.qmd) to summarize cases overal total cases among the years from 2013-2022. It records child abuse-neglect cases in Texas, location of such cases, priority [Priority 1: serious, must be investigated withhin a day. Priority 2: less severe, 72 hr time frame, victim 5 years and older], and operation type with regards to parties involved. 
I decided to use a directed value graph that initailly records the total number of cases between any two links. That means whenever I add an edge, I add the total number of cases associated with that line of the dataset, and if an edge already exists between two said nodes, I add the total cases value to the existing integer on that edge. I know off hte bat that I want to do djakstra on this. However, I didn't want to limit myself to longest/shortest "path" by gross cases. So I converted each of hte links to a decimal percent, where all the successors of a node add up to 1, via the weightsAsPercentage() method. Nick also suggested the awesome idea of inverting the weights (subtracting the percentage from 1) so I can use Djakstra to find the longest path by outcome percentage as well. 

12/8/2023
Dear Diary,
The olive oil tasting event today was horrid. I expected better from the Italian department. Maybe I should've made a graph on the various departments of Smith college, their satisfaction ratings, and the gross spending allocated between them and the events they do, based on the information I extracted from my AEMES mentor. The CS department doesn't deserve those spending cuts.


12/12/2023
Dear Diary,
Project done. 
I figured out the Djakstra algorithm (and added a bunch of hashsets and whatnot to track certain things for animation purposes) in the Shortest.java file. Then I copied Shortest.java into a ShortestbyPercent.java file that allows double inputs instead if integer inputs in case you use a garph with weights as percents. It was fun, but my code is very ugly. A look not even the creator can love. 

12/13/2023
Dear Diary,
I've spelt Dijakstra wrong this entire time. 