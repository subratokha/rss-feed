(function () {
  'use strict';
  
function updateFeeds(result) {
  const feedData = document.getElementById('feed-data');
  while(feedData.firstChild) {
    feedData.removeChild(feedData.firstChild);
  }
  const nodes = result.map(data => {
    const h3 = document.createElement('h3');
      h3.innerHTML = data.title;
    const p = document.createElement('p');
    p.innerHTML = data.description;
    const span = document.createElement('span');
    span.innerHTML = data.pubDate;
    const section = document.createElement('section');
    section.append(h3,p,span);
    return section;
  });
  feedData.append(...nodes);
}

function feedImporter() {
  const feedURL = document.getElementById('cmp-feed').dataset.feedUrl;

  fetch(feedURL).
    then(function (response) {
      if (response.status !== 200) {
        console.log('Looks like there was a problem. Status Code: ' + response.status);
        return;
      }
      
      // Examine the text in the response
      response.json().then(function (data) {
          updateFeeds(data);
        });
    }).
    catch(function (err) {
      console.log('Fetch Error :-S', err);
    });
}
feedImporter();
const feedInterval = document.getElementById('cmp-feed').dataset.feedInterval;
setInterval(feedImporter,feedInterval *1000);
}());
