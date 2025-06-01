# Results

The results of running the algorithms proved what was written in the article on the website - the Greedy Search algorithm works faster than the other two algorithms and produces fewer iterations, but could not find the closest path to the goal on all maps.

The most efficient is A*, it found the shortest path faster than other algorithms on all maps.
#
(time in tables is given in seconds)

| Breadth First Seach | 300x300 | 600x600 | 900x900 |
|-|:-------:|:--------:|:---------:|
| time to find the solution|   0.18287992477416992      |    0.8080260753631592     |  1.7917819023132324       |
| number of iterations|    47233     |     197804    |  450414       |
| length of the solution in steps|   555   |    1248 | 1844    |


| Greedy Search                   | 300x300 |       600x600 | 900x900 |
|---------------------------------|:-------------------:|:--------------:|:---------:|
| time to find the solution       |     0.01802515983581543                |   0.03267168998718262            | 0.13552379608154297      |
| number of iterations            |    3358    |    6293           |    29496     |
| length of the solution in steps |    983    |    1974           |  4130       |



| A*                              | 300x300 |             600x600 | 900x900 |
|---------------------------------|:-------:|:--------------------:|:-----:|
| time to find the solution       | 0.04591846466064453        | 0.36528754234313965 |    0.5945110321044922 |
| number of iterations            |    8202     |               60472 |  93999   |
| length of the solution in steps |   555   |                1248 | 1844 |








