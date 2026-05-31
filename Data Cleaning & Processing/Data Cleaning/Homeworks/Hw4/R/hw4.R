is.na(Diabetes) # returns TRUE of x is missing
y <- c(1,2,3,NA)
is.na(y) # returns a vector (F F F T) 

Diabetes <- c(1,2,NA,3)
mean(Diabetes) # returns NA
mean(Diabetes, na.rm=TRUE) # returns 2

# list rows of data that have missing values
mydata = Diabetes
# create new dataset without missing data
newdata <- na.omit(mydata) 
