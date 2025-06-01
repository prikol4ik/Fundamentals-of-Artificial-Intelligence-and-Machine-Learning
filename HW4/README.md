## example of the input knowledge
([], "fragile"), **#facts**  
([], "falls_down"),   
([], "contains_liquid"),  
(["fragile", "falls_down"], "breaks"), **#rules**  
(["breaks", "contains_liquid"], "makes_a_mess"),    
(["spoiled", "breaks"], "smells")



## the results for queries

egg breaks: True  
egg makes a mess: True  
egg smells: False