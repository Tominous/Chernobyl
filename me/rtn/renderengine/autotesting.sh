OUTLINE="==========================================="
echo $OUTLINE
echo $OUTLINE
echo "STARTING AUTO TEST"
for a in ${@}; do
	echo "TESTING DIR: $a"
	for f in $(ls $a/*.bl); do
	    # read header of test
		FIRSTLINE=$(head -n 1 $f)
		# get line count of command output
	       ant run -Dblaise=$f -Dmal=out.m > out.txt
	       # run the compiler demo
	       compiler-demo $f out1.m > safety.txt
	       COUNT=$(wc -l out.txt | awk '{print $1'})
	       if [[ $FIRSTLINE == *VALID* ]]; then
		       if [ $COUNT -gt 20 ]; 
		       then
			       echo $OUTLINE
			       echo "TEST: $f Passed Tree Construction.. checking MIPS code:"
			       diff out.m out1.m
			       echo $OUTLINE
		       else
			       echo $OUTLINE
			       echo "WARNING: $f should be VALID but was INVALID. Printing output..."
			       echo $OUTLINE
			       cat out.txt
			       echo $OUTLINE
			fi
		
		fi
	      if [[ $FIRSTLINE == *NOT* ]]; 
	      	then
		      if [ $COUNT -gt 20 ];
		      then
			      echo $OUTLINE
			      echo "WARNING: test $f should be invalid, but it COMPILED correctly. Printing output..."
			      echo $OUTLINE
			      cat out.txt
			      echo $OUTLINE
		      else
			      echo $OUTLINE
			      echo "TEST: $f Passed..checking MIPS code:"
			      diff out.m out1.m
			      echo $OUTLINE
			fi
		fi
	done
done