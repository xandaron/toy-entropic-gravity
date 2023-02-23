import numpy as np
import matplotlib.pyplot as plt

data = np.loadtxt('./output/230223123511.csv', delimiter=',')

x = data[:,0]
y = data[:,1]

alpha = 0.021580264926186414
k = 0.8549712637707739

xseq = np.linspace(0, 700, 701)

plt.scatter(x, y,c=x)
plt.plot(xseq, k / (xseq ** alpha))


fig, ax = plt.subplots()
ax.plot(xseq, k * -alpha / (xseq ** -(alpha+1)))
plt.show()