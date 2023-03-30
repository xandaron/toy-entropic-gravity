import numpy as np
import matplotlib.pyplot as plt

# 230223184745
# 230223185207
# 230223213126
# 230224151857
data = np.loadtxt('./output/230224151857.csv', delimiter=',')

x = data[:,0]
y = data[:,2]

fig, ax = plt.subplots(1,3)

logx = np.log(x)
logy = np.log(np.absolute(y))
xseg = np.linspace(min(logx), max(logx), len(logx))
m, c = np.polyfit(logx, logy, 1)
ax[0].scatter(logx, logy, c=logx)
ax[0].plot(xseg, m* xseg + c)
ax[0].set_title(f'ln(|S\'(x)|) = |c + m ln(x)|\nm = {m}\nc = {c}')
ax[0].set_xlabel('ln(x)')
ax[0].set_ylabel('|ln(S\'(x))|')
print(f'm = {m}\nc = {c}')

xseg = np.linspace(min(x), max(x), 10*len(x))

ax[1].scatter(x, y, c=x)
ax[1].plot(xseg, -(xseg**m * np.exp(c)))
ax[1].set_title(f'S\'(x) = (S(x + d) - S(x)) / d\nS\'(x) = -e^c * x^m')
ax[1].set_xlabel('x')
ax[1].set_ylabel('(S(x + d) - S(x)) / d')

k = data[0,1] - -(np.exp(c)/(m+1)) * (data[0,0] ** (m+1))

ax[2].scatter(x, data[:,1], c=x)
ax[2].plot(xseg, -(np.exp(c)/(m+1)) * (xseg ** (m+1)) + k)
ax[2].set_title(f'S(x) = k / x^a\nS(x) = -e^c * x^(m+1) / (m+1) + k\nk = {k}')
ax[2].set_xlabel('x')
ax[2].set_ylabel('S(x)')
plt.show()