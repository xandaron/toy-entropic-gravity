# toy-entropic-gravity
I'm a 3rd year CS student at the Univeristy of York and this is a repository for my dissertation project. 

The project:

There continues to accumulate circumstantial evidence that gravity is not best described by curved space-time as Einstein taught us. Instead, there is a growing sense that it might be best described as an emergent force, just like the random jostling of polymer chains causes the restoring force in a rubber band. Such forces produced by random fluctuations are called entropic.

In this project you will numerically derive the force law in a toy model of gravity called the mikado universe (see the figure below). You could do this either by i) simulating the random action of the model and determining the force law with distance between two "bodies", or by ii) numerically computing the entropy for different configurations of the bodies. A more sophisticated result would be to compare the results of both methods.

(see figure at https://www-users.cs.york.ac.uk/~schmuel/projects.html#sb54.002 )

The mikado universe: The straight lines represent ray paths. The red circles represent two bodies in the mikado universe which exclude certain rays from their volume (the gray rays are excluded). With each time-step a random path is chosen. If it is already drawn (black) it is removed, otherwise if it allows enough free space for the red circles to be drawn near their original location, it is added. As this random process is repeated, the circles approach and fall towards eachother.