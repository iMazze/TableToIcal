// Return a Promise that resolves to jCal data of the ICAL file at `url`.
function get_ical_data(url) {
  return new Promise((resolve, reject) => {
    var req = new XMLHttpRequest();
    req.onreadystatechange = (ev) => {
      if (req.readyState == 4) {
        if (req.status == 200) {
          resolve(ICAL.parse(req.responseText));
        } else {
          reject('Error, HTTP ' + req.status.toString());
        }
      }
    };
    req.onerror = e => reject(e);
    req.open('GET', url, true);
    req.send(null);
  });
}

// Return an EventSource that fetches events from the ICAL file at `url`.
function ical_event_source(url) {
  return {
    id: url,
    events: (start, end, timezone, callback) => {
      get_ical_data(url).then(data => {
        var comp = new ICAL.Component(data);
        var events = comp.getAllSubcomponents('vevent').map(ve => new ICAL.Event(ve));
        var color = comp.getFirstPropertyValue('x-apple-calendar-color');
        callback(
          events
          //TODO: handle recurring events
            .filter(entry => !entry.isRecurring() && (moment(entry.startDate.toJSDate()).isBetween(start, end, null, '[]') || moment(entry.endDate.toJSDate()).isBetween(start, end, null, '[]')))
            .map(entry => {
              return {
                //id: entry.uid,
                title: 'xxx',//entry.summary,
                allDay: entry.startDate.isDate,
                start: entry.startDate.toJSDate(),
                end: entry.endDate.toJSDate(),
                color: color,
                location: entry.location,
                description: entry.description
              };
            }));
      });
    }
  };
}

function httpGet(theUrl)
{
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", theUrl, false ); // false for synchronous request
    xmlHttp.send( null );
    return xmlHttp.responseText;
}

function ical_event_source2(url) {	
	var events = []
	var parsed = ICAL.parse(httpGet(url));
	parsed[2].forEach(function(event) {
	  if (event[0] !== 'vevent') return
	  var summary, location, start, end;
	  event[1].forEach(function(event_item){
		
		switch(event_item[0]){
		  case 'location':
			location = event_item[3]
			break;
		  case 'summary':
			summary = event_item[3]
			break;
		  case 'dtstart':
			start = event_item[3]
			break;
		  case 'dtend':
			end = event_item[3]
			break;
		}
		if (summary && location && start && end) {
		  events.push({
			title: summary,
			start: start,
			end: end,
			location: location
		  })  
		}
	  })
	});
	
	// What the Hell it works
	var events_small = [];
    var i;
    for (i = 0; i < events.length; i++) { 
    	if(i%2==0)
    	{
    		events_small.push(events[i]);
    	}
	}
       
    return events_small;
}