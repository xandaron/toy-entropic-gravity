import numpy as np
import matplotlib.pyplot as plt
import scipy.stats as stats

method1 = np.loadtxt('./output/230403175740.csv', delimiter=',')
method2 = np.loadtxt('./output/230403175921.csv', delimiter=',')

x1 = method1[0,:]
y1 = method1[1:,:]
y1 = np.sum(y1, axis=0)

x2 = method2[0,:]
y2 = method2[1:,:]
y2 = np.sum(y2, axis=0)

fig, ax = plt.subplots(1,2)

ax[0].bar(x1, y1, width=5)
ax[0].set_title('Method 1')
ax[0].set_xlabel('Distance from centre')
ax[0].set_ylabel('Number of illegal lines')

ax[1].bar(x2, y2, width=5)
ax[1].set_title('Method 2')
ax[1].set_xlabel('Distance from centre')
ax[1].set_ylabel('Number of illegal lines')

plt.show()