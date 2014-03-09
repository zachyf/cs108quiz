var width = 960,
    height = 500;

var force = d3.layout.force()
    .charge(-200)
    .linkDistance(50)
    .size([width, height]);

var svg = d3.select("body").append("svg")
    .attr("width", width)
    .attr("height", height)
    .style("display", "block")
    .style("margin", "auto")
    .style("background-color", "white");


d3.csv("edgesFlow.csv", function(links) {
  d3.csv("nodes.csv", function(nodes){
    var hash_lookup = [];
    // make it so we can lookup nodes in O(1):
    nodes.forEach(function(d, i) {
      hash_lookup[d.name] = d;
    });

    links.forEach(function(d, i) {
      d.source = hash_lookup[d.source];
      d.target = hash_lookup[d.target];
    });

    force.nodes(nodes).start();
    force.links(links).start();

      var link = svg.selectAll(".link")
          .data(links)
          .enter().append("line")
          .attr("class", "link")
          .style("stroke-width", function(d){
            return Math.min(50, 1 + 2* d.denominator);
          })
          .on("click", function(d){
            var thisLink = d3.select(this);
            d3.select("#titleHeader").data(thisLink).text(d.source.name + " to " + d.target.name);
            d3.select("#groupHeader").data(thisLink).text("Group: Edge");
            d3.select("#weightHeader").data(thisLink).text("Consumed/Capacity: " + d.numerator + "/" + d.denominator);
          });

      console.log(nodes);
      var node = svg.selectAll(".node")
          .data(nodes)
          .enter().append("circle")
          .attr("class", "node")
          .attr("r", function(d){
            return Math.min(25, 4 + 5* Math.pow(Math.abs(d.magnitude), 1/10));
          })
          .style("fill", function(d) {
            if (d.group == "powerPlant"){
              if (d.magnitude == "0"){
                return "#660809";
              }
              return "black";
            }else if(d.group == 'switch'){
               if (d.magnitude == "-1"){
                return "#660809";
              }
              return "grey";
            }else if(d.group == 'tier1'){
              return "#00067F";
            }else if(d.group == 'tier2'){
              return "#000CFF";
            }else if(d.group == 'tier3'){
              return "#4CB6FF";
            }else{
              if(d.magnitude == ""){
                return "white";
              }
              return "lightblue";
            } 
          })
          .call(force.drag).on("click", function(d){
            var thisCircle = d3.select(this);
            d3.select("#titleHeader").data(thisCircle).text(d.name);
            d3.select("#groupHeader").data(thisCircle).text("Group: " + d.group);
            d3.select("#weightHeader").data(thisCircle).text("Input(-)/Output(+): " + d.magnitude);
          });

      node.append("title")
          .text(function(d) { return d.name; });

      force.on("tick", function() {
        link.attr("x1", function(d) { return d.source.x; })
            .attr("y1", function(d) { return d.source.y; })
            .attr("x2", function(d) { return d.target.x; })
            .attr("y2", function(d) { return d.target.y; });

        node.attr("cx", function(d) { return d.x; })
            .attr("cy", function(d) { return d.y; });

            node.attr("cx", function(d) { return d.x = Math.max(7, Math.min(width - 7, d.x)); }) 
            .attr("cy", function(d) { return d.y = Math.max(7, Math.min(height - 7, d.y)); });
      });

      var borderPath = svg.append("rect")
      .attr("x", 0)
      .attr("y", 0)
      .attr("height", height)
      .attr("width", width)
      .style("stroke", "grey")
      .style("fill", "none")
      .style("stroke-width", "2px");
  })
});

d3.csv("hospitalNeed.csv", function(needs){
   var outputString = "";
   needs.forEach(function(d, i) {
      if(d.need != "0"){
        outputString = outputString + d.name + " Needs " + d.need + "<br>";
      }
    });
   d3.select("#needsList").html("<h3>" + outputString + "</h3>");
});
