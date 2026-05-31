########### Optional: apply to iris data using randomForest ###########

#load the randomForest library. if you havent installed it, run the next line
install.packages("randomForest")
library(randomForest)

sink("phase1-1.txt") # redirect console output to a file
#matrix2<-read.csv('ZscoresM2.csv',header=T)
matrix2 <- maxico2;

#load the iris data
#data(matrix2)

# this data has 150 rows
nrow(matrix2)

# look at the first few
head(matrix2)

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
splits <- splitdf(matrix2, seed=808)

#it returns a list - two data frames called trainset and testset
str(splits)

# there are 75 observations in each data frame
lapply(splits,nrow)

#view the first few columns in each data frame
lapply(splits,head)

# save the training and testing sets as data frames
training <- splits$trainset
testing <- splits$testset

#fit the randomforest model
model <- randomForest(Hot.or.Cold~., data = training, importance=TRUE, keep.forest=TRUE)
print(model)

#what are the important variables (via permutation)
varImpPlot(model, type=1)

#predict the outcome of the testing data
predicted <- predict(model, newdata=testing[ ,-14])

# what is the proportion variation explained in the outcome of the testing data?
# i.e., what is 1-(SSerror/SStotal)
actual <- testing$Num
rsq <- 1-sum((actual-predicted)^2)/sum((actual-mean(actual))^2)
print(rsq)

sink() # restore output to the screen