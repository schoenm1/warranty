clear all
clc

%--------------------------- Aufgabe 1.a)+b)  

%Variablen definieren (damit die Funktion variabel bleibt)
syms y t y2 t2;
%-------- Initialisierung von gegebenen Werten 
%Schrittweite
h=0.01;

%Abbruchkriterien
t_abbr=25; %Wert von t oder
%maxZeitschritt=2; %Anzahl Zeitschritte
tol=10^-2;

%Funktion F1,F2
diff_y(1,1)= 1+y^2*y2-4*y;
diff_y(2,1)= 3*y-y^2*y2;

%Startwerte y1
t_start(1,1)=0;
y_start(1,1)=1.5;

%fuer Berechnung mit h-Anpassung
y_calc(i,1) = y_start(i,1);
t_calc(i,1)=t_start(i,1);

%fuer Berechnung ohne h-Anpassung
y_calc_ohneH(i,1) = y_start(i,1);
t_calc_ohneH(i,1)=t_start(i,1);
end
und so weiter, und so fort....






