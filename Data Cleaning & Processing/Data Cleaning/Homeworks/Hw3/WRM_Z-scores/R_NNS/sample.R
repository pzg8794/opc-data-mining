#source("http://scg.sdsu.edu/wp-content/uploads/2013/09/dataprep.r")

library(nnet)

sink("phase1-2.txt") # redirect console output to a file

#seeds<-read.csv('ZscoresM2.csv')

seeds <- mexico2;
names(seeds)
summary(seeds)


par(mfrow=c(2, 2))

# splitdf function will return a list of training and testing sets
splitdf <- function(dataframe, seed=NULL) {
  if (!is.null(seed)) set.seed(seed)
  index <- 1:nrow(dataframe)
  trainindex <- sample(index, trunc(length(index)/2))
  trainset <- dataframe[trainindex, ]
  testset <- dataframe[-trainindex, ]
  list(trainset=trainset,testset=testset)
}

#apply the function
splits <- splitdf(seeds, seed=808)

#it returns a list - two data frames called trainset and testset
str(splits)

# there are 75 observations in each data frame
lapply(splits,nrow)

#view the first few columns in each data frame
lapply(splits,head)

# save the training and testing sets as data frames
train <- splits$trainset
test <- splits$testset

c=as.data.frame(train)
d=as.data.frame(test)

train$Surv = class.ind(c$Hot.or.Cold)
test$Surv = class.ind(d$Hot.or.Cold)

fitnn = nnet(Surv~Year + Month +	Precipitation	+ MaxTemperature	+ MinTemperature	+ WindSpeed	+ AverageTemp	+ FarainheithTemp	+ Hot.or.Cold, train, size=14, softmax=TRUE)
summary(fitnn)

table(data.frame(predicted=predict(fitnn, test)[,2] > 0.5,actual=test$Surv[,2]>0.5)      
fitnn = nnet(Surv~Year + Month +  Precipitation	+ MaxTemperature	+ MinTemperature	+ WindSpeed	+ AverageTemp	+ FarainheithTemp	+ Hot.or.Cold, train, size=14, softmax=TRUE, Hess=TRUE)
fitnn$Hess
eigen(fitnn$Hess)

sink() # restore output to the screen
