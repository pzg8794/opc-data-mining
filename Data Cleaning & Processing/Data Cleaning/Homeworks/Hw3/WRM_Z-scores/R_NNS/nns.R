install.packages('clusterGeneration')
library(clusterGeneration)

install.packages('nnet')
library (nnet)

sink("phase1-3.txt") # redirect console output to a file

#seeds<-read.csv('ZscoresM2.csv',header=T)

seeds <- mexico2;
seedstrain<- sample(1:294,206)
seedstest <- setdiff(1:294,seedstrain)

ideal <- class.ind(seeds$Num)

seedsANN = nnet(seeds[seedstrain,-13], ideal[seedstrain,], size=15, softmax=TRUE)

predict(seedsANN, seeds[seedstrain,-13], type="class")

table(predict(seedsANN, seeds[seedstest,-13], type="class"),seeds[seedstest,]$Num)

sink() # restore output to the screen
