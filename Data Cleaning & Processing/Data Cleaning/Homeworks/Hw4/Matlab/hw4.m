
mainFolder = '/home/piter/Desktop';
work = '/home/piter/Desktop/Data Cleaning/HW4/Matlab';
cd(work);

% Import the data
data = xlsread('/home/piter/Desktop/Data Cleaning/HW4/Matlab/Diabetes.xlsx','Sheet1');

A = cell(size(data));
A = data;
K = A;
% Allocate imported array to column variable names

%K = prod(size(A));                 % The number of elements in X
%dd = sort(rand(A,1));        % Generate a random vector with K elements 

Patient                         = data(:,1);
No_times_pregnant               = data(:,2);
Plasma_glucose_concentration	= data(:,3);
Diastolic_blood_pressure        = data(:,4);
Tricepts_skin_fold_thickness	= data(:,5);
Two_hr_serum_insulin            = data(:,6);
Body_mass_index                 = data(:,7);
Diabetes_pedigree_function      = data(:,8);
Age                             = data(:,9);
Class                           = data(:,10);  %(1=positive for diabetes, 0 = negative)



sum(A);

i = find(~isnan(A));

x = A(~isnan(A));

B = A;

B(any(isnan(B),2),:) = [];

% Calculate the mean and the standard deviation
mu = mean(A)
sigma = std(A)

% Calculate the number of outliers in each column
[n,p] = size(A)
outliers = abs(A - mu(ones(n, 1),:)) > 3*sigma(ones(n, 1),:);
nout = sum(outliers) 

% To remove the entire row of data containing the outlier, type
A(any(outliers,2),:) = [];

mx = max(A);
mu = mean(A);
sigma = std(A);

[mx,indx] = min(A);
min(A(:));
max(A(:));

[n,p] = size(A); % Get the size of the count matrix
e = ones(n,1); % Define a vector of ones
C = A - e*mu; % Subtract the mean from each matrix element

a = [1 0.2];
%4 Enter the coefficients of the numerator to represent :
b = [2 3];

D = A(:,1);
%The 4-hour moving average of the data is calculated by using
y = filter(b,a,D);
%Compare the original data and the smoothed data with an overlaid plot of the two curves:
t = 1:length(D);
plot(t,D,'-.',t,y,'-'), grid on;
legend('Original Data','Smoothed Data',2);



%dat = ismissing(A);
%dat1 = misdata(dat);
%plot(A,dat)        % Check how the missing data
                      % was estimated on a time plot

% Clear temporary variables
clearvars data raw;
