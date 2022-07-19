FROM nginx
# copy the nginx configuration
COPY nginx.conf /etc/nginx/nginx.conf
# expose load balancer at the following port
EXPOSE 8080
# Start load balancer
CMD ["nginx", "-g", "daemon off;"]