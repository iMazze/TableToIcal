var ical_original;
var ical_new;


function matches(eventA, eventB)
{
	var match = (eventA.title === eventB.title) 
	&& (eventA.start.format() === eventB.start.format())
	//&& (eventA.end.format() === eventB.end.format())
	&& (eventA.location === eventB.location);
	return match
}

function notContainedInList(event, eventList)
{
	var i;
	for(i = 0; i < eventList.length; i++)
	{
		if(matches(event, eventList[i]))
		{
			return false
		}
	}
	return true

}

function ical_compare() 
{	
	var changedEvents = [];
	var i;
	
	//Welche elemente sind neu oder fehlen?
	for(i = 0; i < ical_new.length; i++)
	{
		if(notContainedInList(ical_new[i], ical_original))
		{
			//changedEvents.push(ical_new[i]);
		}
	}
	
	//Welche sind gelöscht
	for(i = 0; i < ical_original.length; i++)
	{
		if(notContainedInList(ical_original[i], ical_new))
		{
			changedEvents.push(ical_original[i]);
		}
	}
	
	
	for(i = 0; i<changedEvents.length; i++){
		console.log(changedEvents[i].title);
	}
}

function push_git()
{



    
}