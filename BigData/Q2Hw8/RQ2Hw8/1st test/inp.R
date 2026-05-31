#install.packages("partykit")
#library("party")
#library(foreign)
#library(party)

kdata <- read.arff("cpu.arff")
View(kdata)

kdata.features = kdata
kdata.features$class <- NULL
View(kdata.features )

results <- kmeans(kdata.features, 3)
results

results$size
results$cluster
results$centers
results$totss
results$withinss
results$tot.withinss
results$betweenss
results$iter
results$ifault

table(kdata$class, results$cluster)
plot(kdata, col = results$cluster)

colnames(kdata)

plot(kdata[c("MYCT" , "MMIN" , "MMAX" , "CACH" , "CHMIN", "CHMAX" , "class")], col=results$cluster)
points(results$centers[,c("MYCT")], col=1:3, pch=8, cex=2)