# ITI0210 Naive Bayes homework

This project implemention a Naive Bayes classifier to categorize news articles by topic (tech, politics, business, entertainment, sport) based on word frequencies.

### Data Preprocessing:
TokenizedFile reads articles from bbc_train.csv and bbc_test.csv. Each article is parsed into a TokenizedEntry that stores its topic and a list of lowercase words (tokens) after removing punctuation.

### Model Training:
ModelImpl uses a Naive Bayes classifier, counting article frequencies per topic for P(c) and word frequencies within each topic for P(w|c). It also calculates vocabulary size.

### Prediction:
predict() calculates each topic’s probability given the words in a new article using log-space for stability. The topic with the highest log probability is chosen.
### Result:

| V ACTUAL       | tech | politics | business | entertainment | sport |
|----------------|------|----------|----------|---------------|-------|
| **tech**       | 20   | 1        | 0        | 0             | 0     |
| **politics**   | 0    | 20       | 1        | 1             | 0     |
| **business**   | 0    | 0        | 22       | 0             | 0     |
| **entertainment** | 0 | 0        | 0        | 18            | 0     |
| **sport**      | 0    | 0        | 0        | 0             | 17    |

### Accuracy: ***97%***    

---







This repository contains a template to aid you in solving this homework.

Please read through this whole file before you start solving as there are quite many hints on how you can solve this that may change how you would approach this problem.
For full points you have to do the following:
- Implement the Naive Bayes classifier in `ModelImpl` that is "mostly correct" (<=10 misclassified answers on test set is acceptable)
- Write a report about your work (what difficulties you ran into, what have you managed to achieve, etc.)
- Include confusion matrix into your report.

## `Main`
Runnable entry point with a simple test case written that you can use it as is to see how your model performs.

It expects to be able to find both `csv` files from where it's ran.
Training file is passed to the model to train and then for every single test case it asks the model to predict the topic based on text.
Once the testing is done it displays a confusion matrix.

## `ConfusionMatrix`

This class calculates the confusion matrix that you have to include into your report.

You do not need to understand this code at all.

Matrix is read this way:
- Rows show the actual value
- Columns show the predicted value

For example, in this matrix:
```
V ACTUAL Apple Orange
Apple       38      1
Orange       2     40
```
We have 38 `Apple`s classified correctly, and one of them was classified as an `Orange`, 40 `Orange`s were classified correctly and 2 of them were classified as an `Apple`.

In other words, correct classifications are in the diagonal, and incorrect ones are everywhere else.

## `TokenizedFile`, `TokenizedEntry` and `TokenizedText`
These classes are used to gradually "tokenize" the input text.

`TokenizedFile` is the top-most container that translates each line of input file into a `TokenizedEntry`.

`TokenizedEntry` is a pair of _lowercase_ topic and `TokenizedText`

`TokenizedText` is a list of every single "word" found within the body of the text. It is slightly filtered to exclude `"`, `.` and `,` characters as well as skip empty tokens.
This is where you can add additional filtering like lowercasing all tokens, removing all numbers and dropping tokens shorter than some threshold (e.g. 3).
It is up to you what kind of filtering you want to implement, and it is a good topic for discussion in your report. (i.e. how each filter added affected your results)

## `Model` and `ModelImpl`
`Model` is a simple interface with `ModelImpl` being a stub of it's implementation.

You are to finish the implementation of `ModelImpl` class.

Please not that model should _not_ have access to the actual topic when it's asked to predict.
In other words if you edit the model to accept `TokenizedEntry` in `predict` and simply return `entry.getTopic()` that is immediately `0` points.
