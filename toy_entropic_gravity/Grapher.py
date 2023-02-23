import numpy as np
import matplotlib.pyplot as plt

data = np.loadtxt('./output/230223184745.csv', delimiter=',')

x = data[:,0]
y = data[:,2]

fig, ax = plt.subplots(1,3)

ax[0].scatter(x, data[:,1], c=x)
ax[0].set_title(f'S(x) = k / x^a\na = NaN\nk = NaN')
ax[0].set_xlabel('x')
ax[0].set_ylabel('S(x)')

ax[1].scatter(x, y, c=x)
ax[1].set_title(f'S\'(x) = (S(x + d) - S(x)) / d')
ax[1].set_xlabel('x')
ax[1].set_ylabel('(S(x + d) - S(x)) / d')

logx = np.log(x)
logy = np.log(np.absolute(y))

ax[2].scatter(logx, logy, c=logx)
ax[2].set_title(f'|ln(S\'(x))|')
ax[2].set_xlabel('ln(x)')
ax[2].set_ylabel('|ln(S\'(x))|')

plt.show()