Imagine at the end of political conference, republicians and democrats are trying to leave the venue and ordering Uber rides at the same time. However to make sure that there is 
no fight, we need to make sure a ride contains the passengers either of the following combinations<br>
1. All 4 republicans<br>
2. All 4 Democrats<br>
3. 2 Democrats and 2 Republicans<br>
All other combination can result in fight<br><br>

Solution: We need to track the count of republicans and democrats, can be done using semaphore variable. Also we need to use barrier so that we any of the valid combinations comes up we can call a  drive.
