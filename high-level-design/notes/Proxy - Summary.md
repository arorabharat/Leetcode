[![Alt text](https://img.youtube.com/vi/xiUmXVcLdCw/0.jpg)](https://www.youtube.com/watch?v=xiUmXVcLdCw)


This video explains proxies in system design, defining them as a way to conceal a computer's identity during network communication (0:25).

It then details two main types:

Forward Proxy: This type hides the client's identity from the server. The video uses an example of accessing US Netflix from Canada, where a friend in the US acts as a proxy, making Netflix think the request originates from the US (1:01). The client's perspective is central here (4:08).
Reverse Proxy: This type hides the server's identity from the client. Using the example of Netflix servers, a reverse proxy (like a load balancer) presents a single endpoint (netflix.com) to users while distributing their requests among many different hidden servers (4:20). This is useful for things like load balancing and traffic shaping, and the server's perspective is central (4:25).
The key takeaway is that both types of proxies are about concealing identity, but from different perspectives: client for forward proxy, and server for reverse proxy (7:37).