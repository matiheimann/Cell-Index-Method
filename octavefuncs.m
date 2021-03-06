function makecircle(r, x, y, c)
  t = linspace(0,2*pi,100); 
  circsx = r.*cos(t) + x; 
  circsy = r.*sin(t) + y; 
  fill(circsx,circsy,c);
  hold on; 
endfunction

function particlesRepresentation(outputFile, staticFile, dynamicFile, id)
  
  M1 = dlmread(staticFile, ' ');
  q = M1(1,1);
  l = M1(2,1);
  
  axis([-l, l, -l, l]);
  radius = zeros(q);
  
  for i = 3:rows(M1)
    radius(i-2) = M1(i,1);
  endfor

  M2 = dlmread(dynamicFile, ' ');
  
  positions = zeros(q,2);
  
  for i = 2:rows(M2)
    positions(i-1,1) = M2(i, 1);
    positions(i-1,2) = M2(i, 2);
  endfor
  
  M3 = dlmread(outputFile, ' ');
  drown = zeros(1, q);
   
  newplot();
  makecircle(radius(M3(id,1) + 1), positions(M3(id,1) + 1, 1), positions(M3(id,1) + 1, 2), 'g');
  drown(M3(1,1) + 1) = 1;
  
  for i = 2:rows(M3)
    makecircle(radius(M3(id,i) + 1), positions(M3(id,i) + 1, 1), positions(M3(id,i) + 1, 2), 'b');
    drown(i) = 1;
  endfor

  for i=1:q
    if(drown(i) == 0)
      makecircle(radius(i), positions(i, 1), positions(i, 2), 'r');
      drown(i) = 1;
    endif
  endfor  
     
  
endfunction