import numpy as np
import matplotlib.pyplot as plt
import scipy.stats as stats

data = np.loadtxt('./output/230330182028.csv', delimiter=',')

x = data[0,:]
y = data[1:,:]

y = np.sum(y, axis=0)
plt.bar(x, y, width=5)
print(stats.chisquare(y, ddof=19)[0])

plt.show()