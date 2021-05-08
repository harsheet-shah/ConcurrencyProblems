Imagine that bucket gets filled with a token at the rate of 1 token per second. The bucket can hold a maximum of N tokens.Implement a thread-safe class that lets threads<br> get a token
when one is available, if no token is available then that requesting thread should be blocked<br>
Solution:The key idea here is to keep a track of number of tokens available at the time of request. Since the rate at which tokens are generated is constant, so if we know <br>
when the token was generated and when a thread had requested, then we can take the difference of that and know number of possible token so far.
