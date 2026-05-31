% Create data for childhood disease cases
measles = [38556 24472 14556 18060 19549 8122 28541 7880 3283 4135 7953 1884];
mumps = [20178 23536 34561 37395 36072 32237 18597 9408 6005 6268 8963 13882];
chickenPox = [37140 32169 37533 39103 33244 23269 16737 5411 3435 6052 12825 23332];



dirList = dir('/Users/pitergarcia/SkyDrive/workspace/bigdata/Sizes/');
names = {dirList.name};
outNames = {};
data = {};
A = {};
B = {};

% graph one
c=0;
p = 0;-
for i=1:numel(names)
    name = names{i};
    if ~isequal(name,'.') && ~isequal(name,'..')
        [~,name] = fileparts(names{i});
        outNames{end+1} = name;
        str = strcat('/Users/pitergarcia/SkyDrive/workspace/bigdata/Sizes/');
        file = load(names{i});
        data{end+1} = file;
        
        rCN  = sort(file(:,1));
        cpuT = sort(file(:,2));
        
        A{end+1} = rCN;
        B{end+1} = cpuT;
        
        if c == 16 || c==0
           c = 0;
           figure;
        end
        
        
        p = p+1;
        c = c+1;
        
       
        
        subplot(4,4,c)
        y1 = rCN;
        y2 = cpuT;
        x = y1+y2;
        
        plot(x, y1, 'r*');
        
        legend('DB SIZE', 0);
        
        title('Double vs Size');
        xlabel('Double');
        ylabel('Size');
        
        c = c+1;
        p = p+1;
        subplot(4,4,c)
        plot(x, y2, 'r*'); % lines only!
        
        legend('DB RSIZE', 0);
        title('Double vs RSize');
        xlabel('Double');
        ylabel('Size');
        
    end
end
