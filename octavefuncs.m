function makecircle(r, x, y, c)
  t = linspace(0,2*pi,50)'; 
  circsx = r.*cos(t) + x; 
  circsy = r.*sin(t) + y;
  circsx = circsy; 
  fill(circsx,circsy, c); 
endfunction

function particlesRepresentation(inputFile, staticFile, dynamicFile)
  radius = {};
end function