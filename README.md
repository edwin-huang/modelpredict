Disclaimer: when the code was written, there was no intent that someone else would see or use it, so it's very spaghettified. The process of running is complex (api1 -> phase1 -> phase2 -> api2 -> phase3 -> phase4), and there's also some functionality that doesn't do much because I didn't add files that had nothing to do with the model on here.

Basically what the model does is: Get user activity data, there is a specific event occuring every 3 hours For each user, split the data based on a specific time (8 choices) and weekend/weekday (2 per week), and record how many times the user was active at the event. It also gets the total number of events in each of these 16/week "sessions". Then, for each user-user pair, use Bayesian statistics to calculate the expected overlap per session, and aggregate it. To get the total expected event overlap. We then use a statistical test called a "Kuiper Test" on each user's distribution of times (array 8 long) to determine the timezone similarity between the two users in the pair. We choose this test because it works well with circular probability distributions. Finally, we see if they share any in-game items that might indicate being an alt, though in most cases this dose not happen. We aggregate the total expected event overlap, actual event overlap, the Kuiper test results, and the item sharing to give a final index. If it is over 15 (semi-arbitrary), then the pair is likely to be an alt.

User data is currently about 1.49 GB (not in this repo), the unused allids.txt contains an additional 142 MB of data, as of 9/18/24.

Precision ~90%
Recall ~70%
Exact values are not known, as most of these alternate accounts are secret, so there is no data to train this model on. Recall estimate is very generous as a result.
